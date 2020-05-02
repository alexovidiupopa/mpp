import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-disciplines',
  templateUrl: './problems.component.html',
  styleUrls: ['./problems.component.css']
})
export class ProblemsComponent implements OnInit {

  constructor(private router: Router) {
    //window.location.reload();
  }

  ngOnInit(): void {
  }

  addProblem() {
    console.log("add problem btn clicked");
    this.router.navigate(["problem/save"]);
  }

  deleteProblem() {
    console.log("delete problem btn clicked");
    this.router.navigate(["problem/delete"]);

  }

  updateProblem() {
    console.log("update problem btn clicked");
    this.router.navigate(["problem/update"]);

  }

  sortProblems(){
    console.log("sort problems btn clicked");
    this.router.navigate(["problem/sort"]);
  }

  filterProblems(){
    console.log("filter problems btn clicked");
    this.router.navigate(["problem/filter"]);
  }


}
