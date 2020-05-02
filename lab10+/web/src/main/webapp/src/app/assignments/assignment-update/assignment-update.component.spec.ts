import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentUpdateComponent } from './assignment-update.component';

describe('AssignmentUpdateComponent', () => {
  let component: AssignmentUpdateComponent;
  let fixture: ComponentFixture<AssignmentUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
