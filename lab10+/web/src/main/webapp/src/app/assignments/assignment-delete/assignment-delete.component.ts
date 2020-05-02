import { Component, OnInit } from '@angular/core';
import {AssignmentService} from "../shared/assignment.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-assignment-delete',
  templateUrl: './assignment-delete.component.html',
  styleUrls: ['./assignment-delete.component.css']
})
export class AssignmentDeleteComponent implements OnInit {

  constructor(private assignmentService: AssignmentService,
              private location: Location
  ) {
  }
  ngOnInit(): void {
  }

  deleteAssignment(sid: number, pid: number) {
    console.log("deleting assignment", sid, pid);
    this.assignmentService.deleteAssignment(sid, pid)
      .subscribe(assignment=>console.log("deleted assignment: ", assignment));
    this.location.back();
  }

  Number(value: string) {
    return Number(value);
  }
}
