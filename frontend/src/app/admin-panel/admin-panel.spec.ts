import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanel } from './admin-panel';

describe('AdminPanel', () => {
  let component: AdminPanel;
  let fixture: ComponentFixture<AdminPanel>;
  // Before each test case of the test suite
  beforeEach(async () => {
    //async operations completed before actual tests
    await TestBed.configureTestingModule({
      imports: [AdminPanel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPanel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
