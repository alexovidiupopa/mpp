import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemSaveComponent } from './problem-save.component';

describe('ProblemSaveComponent', () => {
  let component: ProblemSaveComponent;
  let fixture: ComponentFixture<ProblemSaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemSaveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemSaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
