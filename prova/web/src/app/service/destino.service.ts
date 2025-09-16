import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DestinoService {
  private readonly API_URL = 'http://localhost:8080/api/destinos';

  constructor(private http: HttpClient) { }

  getAllDestinos(): Observable<any> {
    return this.http.get(this.API_URL);
  }

  createDestino(destino: any): Observable<any> {
    return this.http.post(this.API_URL, destino);
  }

  deleteDestino(id: any): Observable<any> {
    return this.http.delete(`${this.API_URL}/${id}`);
  }

}
