import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class StepService {
  baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  startProcedure(certificateId: string, userId: string) {
    return this.http.post(this.baseUrl + 'start_procedure', {
      certificateID: certificateId,
      userId: userId,
    });
  }
}
