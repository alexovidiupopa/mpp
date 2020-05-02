import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Student} from "./student.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";


@Injectable()
export class StudentService {
  private studentsUrl = 'http://localhost:8080/api/students';

  constructor(private httpClient: HttpClient) {
  }

  getStudents(): Observable<Student[]> {
    return this.httpClient
      .get<Array<Student>>(this.studentsUrl);
  }

  getStudent(id: number): Observable<Student> {
    return this.getStudents()
      .pipe(
        map(students => students.find(student => student.id === id))
      );
  }

  saveStudent(student: Student): Observable<Student> {
    console.log("saveStudent", student);

    return this.httpClient
      .post<Student>(this.studentsUrl, student);
  }

  update(student): Observable<Student> {
    const url = `${this.studentsUrl}/${student.id}`;
    return this.httpClient
      .put<Student>(url, student);
  }

  deleteStudent(id: number): Observable<any> {
    const url = `${this.studentsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

  filterStudents(name: string) {
    const url=`${this.studentsUrl}/filter/${name}`;
    return this.httpClient.get<Array<Student>>(url);
  }

  sortStudents() {
    const url=`${this.studentsUrl}/sort`;
    return this.httpClient.get<Array<Student>>(url);

  }
}
