import { Component, OnInit } from '@angular/core';
import {StudentService} from "../shared/student.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-student-update',
  templateUrl: './student-update.component.html',
  styleUrls: ['./student-update.component.css']
})
export class StudentUpdateComponent implements OnInit {

  constructor(private studentService: StudentService,
              private location: Location
  ) {
  }
  ngOnInit(): void {
  }

  updateStudent(id:number,serialNumber: string, name: string, groupNumber: number) {
    if (id==0 || serialNumber=="" || name=="" || groupNumber==0){
      console.log("null credentials");
      return;
    }
    console.log("updating student", serialNumber, name, groupNumber);

    this.studentService.update({
      id: +id,
      serialNumber,
      name,
      groupNumber: +groupNumber
    })
      .subscribe(student => console.log("updated student: ", student));

    this.location.back(); // ...
  }

  Number(value: string) {
    return Number(value);
  }
}
