import { Component, OnInit } from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {Location} from "@angular/common";
import {LabProblem} from "../shared/problem.model";

@Component({
  selector: 'app-problem-filter',
  templateUrl: './problem-filter.component.html',
  styleUrls: ['./problem-filter.component.css']
})
export class ProblemFilterComponent implements OnInit {

  errorMessage: string;
  problems: Array<LabProblem>;
  problemsWithAngular: Array<LabProblem> = [];
  constructor(private problemService: LabProblemsService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  filterProblems(score: string) {
    this.problemService.filterProblems(score)
      .subscribe(
        problems => this.problems = problems,
        error => this.errorMessage = <any>error
      );

  }


  filterProblemsWithAngular(value: string) {
    this.problemService.getProblems()
      .subscribe(problems=>this.problemsWithAngular=problems.filter(problem=>problem.score>=Number(value)));
  }
}
