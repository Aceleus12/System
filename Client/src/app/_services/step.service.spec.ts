/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { StepService } from './step.service';

describe('Service: Step', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StepService]
    });
  });

  it('should ...', inject([StepService], (service: StepService) => {
    expect(service).toBeTruthy();
  }));
});
