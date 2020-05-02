import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentSortComponent } from './student-sort.component';

describe('StudentSortComponent', () => {
  let component: StudentSortComponent;
  let fixture: ComponentFixture<StudentSortComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudentSortComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
