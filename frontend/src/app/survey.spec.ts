import { TestBed } from '@angular/core/testing';

import { Survey } from './survey';

describe('Survey', () => {
  let service: Survey;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Survey);
  });

  it('should be created', () => {
    // toBeTruthy from Jasmine
    // All javascript falsy values not allowed
    expect(service).toBeTruthy();
  });
});
