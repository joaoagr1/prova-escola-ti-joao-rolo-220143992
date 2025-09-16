import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ViagemService } from './service/viagem.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{

  viagens : any;

  constructor(private viagemService : ViagemService) { }

  ngOnInit(): void {

    this.loadViagens();
    console.log(this.viagens)
  }

  private loadViagens() {
    this.viagemService.getAllViagens().subscribe(res => this.viagens = res)
  }
}
