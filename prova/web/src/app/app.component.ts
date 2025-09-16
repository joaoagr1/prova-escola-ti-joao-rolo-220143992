import { Component, OnInit } from '@angular/core';
import { ViagemService } from './service/viagem.service';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DestinoService } from './service/destino.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatListModule } from '@angular/material/list';

interface Destino {
  id?: number;
  nome: string;
}

interface Viagem {
  id?: number;
  nome: string;
  dataSaida: string;
  dataChegada: string;
  valor: number;
  destinos: Destino[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DatePipe, FormsModule, CommonModule, CurrencyPipe, DatePipe, FormsModule,
    MatToolbarModule, MatCardModule, MatFormFieldModule, MatInputModule,
    MatButtonModule, MatIconModule, MatTableModule, MatChipsModule, MatListModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

  viagens: Viagem[] = [];
  destinos: Destino[] = [];

  currentViagem: Viagem = this.getDefaultViagem();
  newDestino: Destino = { nome: '' };
  editingViagem = false;

  constructor(
    private viagemService: ViagemService,
    private destinoService: DestinoService
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData() {
    this.loadViagens();
    this.loadDestinos();
  }

  private loadViagens() {
    this.viagemService.getAllViagens().subscribe({
      next: (res: Viagem[]) => this.viagens = res,
      error: (e) => console.error('Erro ao carregar viagens:', e)
    });
  }

  private loadDestinos() {
    this.destinoService.getAllDestinos().subscribe({
      next: (res: Destino[]) => this.destinos = res,
      error: (e) => console.error('Erro ao carregar destinos:', e)
    });
  }

  saveViagem() {
    if (this.editingViagem) {
      this.viagemService.updateViagem(this.currentViagem).subscribe({
        next: () => this.afterSaveViagem(),
        error: (e) => console.error('Erro ao atualizar viagem:', e)
      });
    } else {
      this.viagemService.createViagem(this.currentViagem).subscribe({
        next: () => this.afterSaveViagem(),
        error: (e) => console.error('Erro ao criar viagem:', e)
      });
    }
  }

  editViagem(viagem: Viagem) {
    this.editingViagem = true;
    this.currentViagem = { ...viagem };
  }

  deleteViagem(id: number) {
    if (confirm('Tem certeza que deseja excluir esta viagem?')) {
      this.viagemService.deleteViagem(id).subscribe({
        next: () => this.loadViagens(),
        error: (e) => console.error('Erro ao excluir viagem:', e)
      });
    }
  }

  cancelEditViagem() {
    this.editingViagem = false;
    this.currentViagem = this.getDefaultViagem();
  }

  private afterSaveViagem() {
    this.loadViagens();
    this.cancelEditViagem();
  }

  createDestino() {
    this.destinoService.createDestino(this.newDestino).subscribe({
      next: () => {
        this.newDestino = { nome: '' };
        this.loadDestinos();
      },
      error: (e) => console.error('Erro ao criar destino:', e)
    });
  }

  addDestinoToViagem(destino: Destino) {

    if (!this.currentViagem.destinos.some(d => d.id === destino.id)) {
      this.currentViagem.destinos.push(destino);
    }

    console.log(this.currentViagem.destinos)

  }

  removeDestinoFromViagem(destino: Destino) {
    this.currentViagem.destinos = this.currentViagem.destinos.filter(d => d.id !== destino.id);
  }

  openAddDestinoModal(viagem: any) {

    const nomeDestino = prompt('Digite o nome do destino para adicionar à viagem:');

    if (nomeDestino) {
      const novoDestino = { nome: nomeDestino };
      this.viagemService.addDestinoToViagem(viagem.id, novoDestino).subscribe({
        next: () => {
          alert('Destino adicionado com sucesso!');
          this.loadViagens();
        },
        error: (error) => {
          console.error('Erro ao adicionar destino:', error);
          alert('Erro ao adicionar destino.');
        }
      });
    }
  }

  openExcludeDestinoModal(viagem: any) {

    const nomeDestino = prompt('Digite o nome do destino para excluir:');

    if (nomeDestino) {
      const novoDestino = { nome: nomeDestino };
      this.viagemService.excludeDestinoToViagem(viagem.id, novoDestino).subscribe({
        next: () => {
          alert('Destino removido com sucesso!');
          this.loadViagens();
        },
        error: (error) => {
          console.error('Erro ao remover destino:', error);
          alert('Erro ao remover destino.');
        }
      });
    }
  }


  private getDefaultViagem(): Viagem {
    return {
      nome: '',
      dataSaida: '',
      dataChegada: '',
      valor: 0,
      destinos: []
    };
  }

  deleteDestino(destino: Destino) {
console.log("nathan")
    this.destinoService.deleteDestino(destino.id).subscribe({
      next: () => this.loadDestinos(),
      error: (e) => console.error('Erro ao deletar:', e)
    });

  }

}
