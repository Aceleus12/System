import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ChargeService {

constructor(private http: HttpClient) { }

charge(model: any): Observable<any> {
  
  return this.http.post<any>('http://localhost:8080/charge-sec', model).pipe(
    map((response: any) => {
      const x = response;
      return x;
    })
  );
}

addMoney(id: string, num: number) {
  return this.http.post('http://localhost:8080/addmoney/' + id + '?money=' + num, {});
}

}
