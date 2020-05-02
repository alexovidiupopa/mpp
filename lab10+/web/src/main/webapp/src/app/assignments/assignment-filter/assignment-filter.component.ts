import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignment-filter',
  templateUrl: './assignment-filter.component.html',
  styleUrls: ['./assignment-filter.component.css']
})
export class AssignmentFilterComponent implements OnInit {

  errorMessage: string;
  assignments: Array<Assignment>;
  constructor(private assignmentService: AssignmentService,
              private location: Location
  ) {

  }

  ngOnInit(): void {
  }

  filterAssignments(value: string) {
    console.log("filter assignments ",value);

    this.assignmentService.filterAssignments(value)
      .subscribe(
        assignments => this.assignments = assignments,
        error => this.errorMessage = <any>error
      );
  }
}
