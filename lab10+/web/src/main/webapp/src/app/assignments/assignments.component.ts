import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  saveAssignment() {
    console.log("add new assignment btn clicked ");

    this.router.navigate(["assignment/save"]);
  }

  deleteAssignment() {
    console.log("delete assignment btn clicked");

    this.router.navigate(["assignment/delete"]);
  }

  updateAssignment() {
    console.log("update assignment btn clicked");

    this.router.navigate(["assignment/update"]);
  }

  filterAssignments() {
    console.log("filter assignments btn clicked");

    this.router.navigate(["assignment/filter"]);
  }

  sortAssignments() {
    console.log("sort assignments btn clicked");

    this.router.navigate(["assignment/sort"]);

  }
}
