import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentSortComponent } from './assignment-sort.component';

describe('AssignmentSortComponent', () => {
  let component: AssignmentSortComponent;
  let fixture: ComponentFixture<AssignmentSortComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentSortComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
