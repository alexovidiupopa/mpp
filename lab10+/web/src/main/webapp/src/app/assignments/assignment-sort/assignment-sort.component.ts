import { Component, OnInit } from '@angular/core';
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-assignment-sort',
  templateUrl: './assignment-sort.component.html',
  styleUrls: ['./assignment-sort.component.css']
})
export class AssignmentSortComponent implements OnInit {

  errorMessage: string;
  assignments: Array<Assignment>;
  constructor(private assignmentService: AssignmentService,
              private location: Location
  ) {
    this.sortAssignments();
  }

  ngOnInit(): void {
  }

  sortAssignments() {
    console.log("sort assignments");

    this.assignmentService.sortAssignments()
      .subscribe(
        assignments => this.assignments = assignments,
        error => this.errorMessage = <any>error
      );
  }
}
