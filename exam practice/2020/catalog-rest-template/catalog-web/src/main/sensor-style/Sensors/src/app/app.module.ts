import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SensorLOLComponent } from './sensor-lol/sensor-lol.component';
import {SensorService} from "./sensor-service.service";
import {HttpClientModule} from "@angular/common/http";
import { SensorsComponent } from './sensors/sensors.component';

@NgModule({
  declarations: [
    AppComponent,
    SensorLOLComponent,
    SensorsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [SensorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
