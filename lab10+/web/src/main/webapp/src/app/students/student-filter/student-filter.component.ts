import { Component, OnInit } from '@angular/core';
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";
import {Student} from "../shared/student.model";

@Component({
  selector: 'app-student-filter',
  templateUrl: './student-filter.component.html',
  styleUrls: ['./student-filter.component.css']
})
export class StudentFilterComponent implements OnInit {
  errorMessage: string;
  students: Array<Student>;

  constructor(private studentService: StudentService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  filterStudents(name: string) {
    this.studentService.filterStudents(name)
      .subscribe(
        students => this.students = students,
        error => this.errorMessage = <any>error
      );
  }
}
