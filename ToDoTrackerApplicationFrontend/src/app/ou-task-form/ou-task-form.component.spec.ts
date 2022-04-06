import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OuTaskFormComponent } from './ou-task-form.component';

describe('OuTaskFormComponent', () => {
  let component: OuTaskFormComponent;
  let fixture: ComponentFixture<OuTaskFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OuTaskFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OuTaskFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
