import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminResource } from './admin-resource';

describe('AdminResource', () => {
  let component: AdminResource;
  let fixture: ComponentFixture<AdminResource>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminResource]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminResource);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
