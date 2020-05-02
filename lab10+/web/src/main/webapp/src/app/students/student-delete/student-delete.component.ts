import { Component, OnInit } from '@angular/core';
import {StudentService} from "../shared/student.service";
import {Location} from "@angular/common";
@Component({
  selector: 'app-student-delete',
  templateUrl: './student-delete.component.html',
  styleUrls: ['./student-delete.component.css']
})
export class StudentDeleteComponent implements OnInit {

  constructor(private studentService: StudentService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }


  Number(value: string) {
    return Number(value);
  }

  removeStudent(id: number) {
    console.log("deleting student ", id);

    this.studentService.deleteStudent(id)
      .subscribe(student => console.log("deleted student: ", student));

    this.location.back(); // ...
  }
}

