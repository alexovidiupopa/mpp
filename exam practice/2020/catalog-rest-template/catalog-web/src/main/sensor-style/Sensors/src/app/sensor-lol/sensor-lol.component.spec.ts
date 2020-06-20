import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorLOLComponent } from './sensor-lol.component';

describe('SensorLOLComponent', () => {
  let component: SensorLOLComponent;
  let fixture: ComponentFixture<SensorLOLComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SensorLOLComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SensorLOLComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
