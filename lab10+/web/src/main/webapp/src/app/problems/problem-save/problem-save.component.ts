import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {LabProblemsService} from "../shared/problems.service";

@Component({
  selector: 'app-problem-save',
  templateUrl: './problem-save.component.html',
  styleUrls: ['./problem-save.component.css']
})
export class ProblemSaveComponent implements OnInit {

  constructor(private problemService: LabProblemsService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveProblem(id: number, desc: string, score: number) {
    console.log("saving problem ", id, desc, score);

    this.problemService.saveProblem({
      id: id,
      description: desc,
      score: score
    })
      .subscribe(problem => console.log("saved problem: ", problem));

    this.location.back(); // ...
  }

  Number(value: string) {
    return Number(value);
  }
}
