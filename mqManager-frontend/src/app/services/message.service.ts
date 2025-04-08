import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message } from '../models/message.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private MESSAGES_API_SERVER = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }


   getMessages(page: number, size: number): Observable<Page<Message>> {
    return this.http.get<Page<Message>>(`${this.MESSAGES_API_SERVER}?page=${page}&size=${size}`);
  }

  getMessageDetails(id: number): Observable<Message> {
    return this.http.get<Message>(`${this.MESSAGES_API_SERVER}/${id}`);
  }
}