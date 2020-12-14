import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
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

  addFile(stepId: string, file: FormData) {
    return this.http.put(this.baseUrl + 'gained_step/add_file/' + stepId, file);
  }

  addComment(stepId: string, comment: string) {
    debugger;
    return this.http.put(
      this.baseUrl +
        'gained_step/add_comment/' +
        stepId +
        '?comment=' +
        comment,
      {}
    );
  }

  confirmStep(stepId: string) {
    return this.http.post(this.baseUrl + 'step/confirm/' + stepId, {});
  }

  downloadFile(id: string) {
    window.open('http://localhost:8080/api/file/' + id);
  }

  addFileFromAdmin(stepId: string, file: FormData) {
    return this.http.put(this.baseUrl + 'step/add_file/' + stepId, file);
  }
}
