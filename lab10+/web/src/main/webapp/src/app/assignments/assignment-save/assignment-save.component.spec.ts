import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentSaveComponent } from './assignment-save.component';

describe('AssignmentSaveComponent', () => {
  let component: AssignmentSaveComponent;
  let fixture: ComponentFixture<AssignmentSaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentSaveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentSaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
