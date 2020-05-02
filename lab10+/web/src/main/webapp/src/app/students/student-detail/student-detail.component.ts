import {Component, Input, OnInit} from '@angular/core';
import {StudentService} from "../shared/student.service";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';
import {Student} from "../shared/student.model";

import {switchMap} from "rxjs/operators";


@Component({
  selector: 'ubb-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css'],
})

export class StudentDetailComponent implements OnInit {

  @Input() student: Student;

  constructor(private studentService: StudentService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.studentService.getStudent(+params['id'])))
      .subscribe(student => this.student = student);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.studentService.update(this.student)
      .subscribe(_ => this.goBack());
  }
}
