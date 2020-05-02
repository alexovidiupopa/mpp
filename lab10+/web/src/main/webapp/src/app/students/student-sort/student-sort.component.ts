import { Component, OnInit } from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student-sort',
  templateUrl: './student-sort.component.html',
  styleUrls: ['./student-sort.component.css']
})
export class StudentSortComponent implements OnInit {
  errorMessage: string;
  students: Array<Student>;

  constructor(private studentService: StudentService,
              private router: Router) {
    this.studentService.sortStudents()
      .subscribe(
        students => this.students = students,
        error => this.errorMessage = <any>error
      );
  }

  ngOnInit(): void {
  }

}
