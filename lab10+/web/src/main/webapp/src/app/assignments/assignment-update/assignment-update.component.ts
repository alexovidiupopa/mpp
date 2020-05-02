import { Component, OnInit } from '@angular/core';
import {AssignmentService} from "../shared/assignment.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-assignment-update',
  templateUrl: './assignment-update.component.html',
  styleUrls: ['./assignment-update.component.css']
})
export class AssignmentUpdateComponent implements OnInit {

  constructor(private assignmentService: AssignmentService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  updateAssignment(sid: number, pid: number, grade: number) {
    console.log("updating assignment", sid, pid, grade);
    this.assignmentService.updateAssignment({
      id:{first:sid, second:pid},
      grade:grade
    })
      .subscribe(assignment=>console.log("updated assignment: ", assignment));
    this.location.back();
  }

  Number(value: string) {
    return Number(value);
  }
}
