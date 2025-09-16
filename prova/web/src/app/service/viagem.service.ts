import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ViagemService {
  private readonly API_URL = 'http://localhost:8080/api/viagens';

  constructor(private http: HttpClient) { }

  getAllViagens(): Observable<any> {
    return this.http.get(this.API_URL);
  }

  createViagem(viagem: any): Observable<any> {
    console.log(viagem)
    return this.http.post(this.API_URL, viagem);
  }

  updateViagem(viagem: any): Observable<any> {
    return this.http.put(`${this.API_URL}/${viagem.id}`, viagem);
  }

  deleteViagem(id: any): Observable<any> {
    console.log("nathan")
    return this.http.delete(`${this.API_URL}/${id}`);
  }


  addDestinoToViagem(viagemId: number, destino: any): Observable<any> {
    return this.http.post(`${this.API_URL}/${viagemId}/destinos`, destino);
  }

  excludeDestinoToViagem(viagemId: number, destinoName: any): Observable<any> {
    console.log(destinoName)
    return this.http.delete(`${this.API_URL}/${viagemId}/destinos/name?destinoName=${destinoName}`);
  }
}
