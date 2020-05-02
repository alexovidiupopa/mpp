import {Component, OnInit} from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {LabProblem} from "../shared/problem.model";

@Component({
  selector: 'app-discipline-list',
  templateUrl: './problem-list.component.html',
  styleUrls: ['./problem-list.component.css']
})
export class ProblemListComponent implements OnInit {
  problems: LabProblem[];

  constructor(private labProblemsService: LabProblemsService) {
  }

  ngOnInit(): void {
    this.labProblemsService.getProblems()
      .subscribe(problems => this.problems = problems);
  }

}
