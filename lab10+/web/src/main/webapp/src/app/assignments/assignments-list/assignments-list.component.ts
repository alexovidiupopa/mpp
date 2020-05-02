import { Component, OnInit } from '@angular/core';
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list.component.html',
  styleUrls: ['./assignments-list.component.css']
})
export class AssignmentsListComponent implements OnInit {
  assignments: Assignment[];
  constructor(private assignmentService: AssignmentService) { }

  ngOnInit(): void {
    this.assignmentService.getAssignments()
      .subscribe(assignments=>this.assignments=assignments);
  }

}
