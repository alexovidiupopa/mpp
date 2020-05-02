import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignment-save',
  templateUrl: './assignment-save.component.html',
  styleUrls: ['./assignment-save.component.css']
})
export class AssignmentSaveComponent implements OnInit {

  constructor(private assignmentService: AssignmentService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveAssignment(sid: number, pid: number) {
    console.log("saving assignment", sid, pid);
    this.assignmentService.saveAssignment({
      id:{first:sid, second:pid},
      grade:0
    })
      .subscribe(assignment=>console.log("saved assignment: ", assignment));
    this.location.back();
  }

  Number(value: string) {
    return Number(value);
  }
}
