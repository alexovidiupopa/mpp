import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentFilterComponent } from './assignment-filter.component';

describe('AssignmentFilterComponent', () => {
  let component: AssignmentFilterComponent;
  let fixture: ComponentFixture<AssignmentFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
