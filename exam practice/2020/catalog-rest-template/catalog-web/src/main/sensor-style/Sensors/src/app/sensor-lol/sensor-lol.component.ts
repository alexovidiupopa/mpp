import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SensorService} from "../sensor-service.service";
import {Sensor} from "../model";
import {Location} from '@angular/common'

@Component({
  selector: 'app-sensor-lol',
  templateUrl: './sensor-lol.component.html',
  styleUrls: ['./sensor-lol.component.css']
})
export class SensorLOLComponent implements OnInit,OnDestroy {

  sensors: Sensor[];
  timer:any;
  constructor(private sensorService: SensorService,
              private location: Location,
              private router: Router) { }

  ngOnInit(): void {
    console.log("here")
    this.sensorService.getAllSensors().subscribe(
      sensors => this.sensors=sensors
    )
    this.timer = setInterval(()=>{this.sensorService.getAllSensors().subscribe(
      sensors => this.sensors=sensors)},5000);

  }


  ngOnDestroy(): void{
    if( this.timer)
    {
      clearInterval(this.timer);
    }
  }



}
