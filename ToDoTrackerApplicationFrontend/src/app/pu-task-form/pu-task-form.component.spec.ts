import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuTaskFormComponent } from './pu-task-form.component';

describe('PuTaskFormComponent', () => {
  let component: PuTaskFormComponent;
  let fixture: ComponentFixture<PuTaskFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PuTaskFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PuTaskFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
