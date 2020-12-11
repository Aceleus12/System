import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { StepToReturn } from '../_models/stepToReturn';

@Injectable({
  providedIn: 'root',
})
export class GainedStepsService {
  baseUrl = environment.apiUrl + 'gained_certificates/';
  constructor(private http: HttpClient) {}

  getUncofrimedForUser(certID: string, userID: string): Observable<StepToReturn[]> {
    return this.http
      .get<StepToReturn[]>(this.baseUrl + 'unconfirmed/' + certID + '/' + userID, { observe: 'response'})
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }
}
