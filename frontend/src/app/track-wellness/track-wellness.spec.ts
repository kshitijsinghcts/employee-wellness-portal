import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackWellness } from './track-wellness';

describe('TrackWellness', () => {
  let component: TrackWellness;
  let fixture: ComponentFixture<TrackWellness>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackWellness]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackWellness);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
