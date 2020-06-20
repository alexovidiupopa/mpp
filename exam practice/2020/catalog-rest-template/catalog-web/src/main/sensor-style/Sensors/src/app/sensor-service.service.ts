import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Sensor} from './model';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SensorService {
  private url = 'http://localhost:8080/api/all';
  private urlSensors = 'http://localhost:8080/api/sensors';
  constructor(private httpClient: HttpClient) { }


  getAllSensors(): Observable<Sensor[]> {

    return this.httpClient
      .get<Array<Sensor>>(this.url);
  }

  getAllSensorsNames(): Observable<String[]> {

    return this.httpClient
      .get<Array<String>>(this.urlSensors);
  }

  Show(sensorName: String): Observable<Sensor[]> {

    const urlNew = `${this.urlSensors}/${sensorName}`;
    return this.httpClient
      .get<Array<Sensor>>(urlNew);
  }

  Kill(sensorName: String): Observable<any>
  {
    const urlKill = `http://localhost:8080/api/kill/${sensorName}`;
    return this.httpClient
      .get<any>(urlKill);
  }
}
