import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemSortComponent } from './problem-sort.component';

describe('ProblemSortComponent', () => {
  let component: ProblemSortComponent;
  let fixture: ComponentFixture<ProblemSortComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemSortComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
