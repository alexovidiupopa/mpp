import {Component, OnDestroy, OnInit} from '@angular/core';
import {Sensor} from "../model";
import {SensorService} from "../sensor-service.service";
import {Location} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sensors',
  templateUrl: './sensors.component.html',
  styleUrls: ['./sensors.component.css']
})
export class SensorsComponent implements OnInit,OnDestroy {

  sensorsName: String[];
  sensorsWithEverything: Map<String,Sensor[]>=new Map<String, Sensor[]>();
  timer: any;



  constructor(private sensorService: SensorService,
              private location: Location,
              private router: Router) { }

  ngOnInit(): void {

    this.sensorService.getAllSensorsNames().subscribe(
      sensors => this.sensorsName=sensors
    )


  }

  ngOnDestroy(): void{
      if( this.timer)
      {
        clearInterval(this.timer);
      }
  }

  Show()
  {
    this.sensorsName.forEach(sensor=>{
    this.sensorService.Show(sensor).subscribe(
      sensors => this.sensorsWithEverything.set(sensor,sensors)
    )});
    this.timer = setInterval(()=>{this.sensorsName.forEach(sensor=>{
      this.sensorService.Show(sensor).subscribe(
        sensors => this.sensorsWithEverything.set(sensor,sensors)
      )});},5000)

  }

  stop(sensor: String) {

    this.sensorService.Kill(sensor).subscribe()
  }


}
