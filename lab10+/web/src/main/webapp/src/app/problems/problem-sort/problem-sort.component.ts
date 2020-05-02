import { Component, OnInit } from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {Location} from "@angular/common";
import {LabProblem} from "../shared/problem.model";

@Component({
  selector: 'app-problem-sort',
  templateUrl: './problem-sort.component.html',
  styleUrls: ['./problem-sort.component.css']
})
export class ProblemSortComponent implements OnInit {

  errorMessage: string;
  problems: Array<LabProblem>;
  constructor(private problemService: LabProblemsService,
              private location: Location
  ) {
    this.problemService.sortProblems()
      .subscribe(
        problems => this.problems = problems,
        error => this.errorMessage = <any>error
      );
  }

  ngOnInit(): void {
  }

}
