import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Certificate } from '../_models/certificate';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { CertificateToAdd } from '../_models/certificateToAdd';

@Injectable({
  providedIn: 'root',
})
export class CertificateService {
  baseUrl = environment.apiUrl + 'certificates/';

  constructor(private http: HttpClient) {}

  getCertificates(): Observable<Certificate[]> {
    return this.http
      .get<Certificate[]>(this.baseUrl, { observe: 'response' })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  addCertificate(certificate: CertificateToAdd) {
    return this.http.post(this.baseUrl, certificate);
  }

  deleteCertificate(id: string) {
    return this.http.delete(this.baseUrl + id);
  }

  getFilteredCerts(word?: string): Observable<Certificate[]> {
    const params = new HttpParams().set('name', word);
    return this.http
      .get<Certificate[]>(this.baseUrl + 'filtered', {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  getCertificate(id: string): Observable<Certificate> {
    return this.http
      .get<Certificate>(this.baseUrl + id, {
        observe: 'response'
      })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  
}
