import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SensorLOLComponent} from "./sensor-lol/sensor-lol.component";
import {SensorsComponent} from "./sensors/sensors.component";


const routes: Routes = [
  {path: 'all', component: SensorLOLComponent},
  {path: 'sensors', component: SensorsComponent},
  {path: '**', redirectTo:'all'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
