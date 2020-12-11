/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GainedStepsService } from './gained-steps.service';

describe('Service: GainedSteps', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GainedStepsService]
    });
  });

  it('should ...', inject([GainedStepsService], (service: GainedStepsService) => {
    expect(service).toBeTruthy();
  }));
});
