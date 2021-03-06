import {Component, OnInit} from '@angular/core';
import {StudentService} from "../shared/student.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-student-new',
  templateUrl: './student-new.component.html',
  styleUrls: ['./student-new.component.css']
})
export class StudentNewComponent implements OnInit {

  constructor(private studentService: StudentService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveStudent(id:string, serialNumber: string, name: string, groupNumber: string) {
    if (id=="" || serialNumber=="" || name=="" || groupNumber==""){
      console.log("null credentials");
      return;
    }
    var regex = /[a-z]/g;
    if (!regex.test(name)){
      console.log("name doesn't match the regex");
      return;
    }
    console.log("saving student", serialNumber, name, groupNumber);

    this.studentService.saveStudent({
      id: Number(id),
      serialNumber,
      name,
      groupNumber: +groupNumber
    })
      .subscribe(student => console.log("saved student: ", student));

    this.location.back(); // ...
  }
}
