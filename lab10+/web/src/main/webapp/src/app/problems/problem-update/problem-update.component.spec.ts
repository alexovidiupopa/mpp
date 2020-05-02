import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemUpdateComponent } from './problem-update.component';

describe('ProblemUpdateComponent', () => {
  let component: ProblemUpdateComponent;
  let fixture: ComponentFixture<ProblemUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
