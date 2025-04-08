import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partner } from '../models/partner.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  private PARTNERS_API_SERVER = 'http://localhost:8080/api/partners';

  constructor(private http: HttpClient) { }

  getPartners(page: number, size: number): Observable<Page<Partner>> {
    return this.http.get<Page<Partner>>(this.PARTNERS_API_SERVER);
  }

  createPartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(this.PARTNERS_API_SERVER, partner);
  }

  updatePartner(id: number, partner: Partner): Observable<Partner> {
    return this.http.put<Partner>(`${this.PARTNERS_API_SERVER}/${id}`, partner);
  }

  deletePartner(id: number): Observable<void> {
    return this.http.delete<void>(`${this.PARTNERS_API_SERVER}/${id}`);
  }
}