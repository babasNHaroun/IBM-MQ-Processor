import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MqService {
  private apiUrl = 'http://localhost:8080/api/mq';

  constructor(private http: HttpClient) { }

  sendMessage(content: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/send`, content, {
      headers: { 'Content-Type': 'text/plain' }
    });
  }

}