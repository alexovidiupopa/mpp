import { Component, OnInit } from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-problem-update',
  templateUrl: './problem-update.component.html',
  styleUrls: ['./problem-update.component.css']
})
export class ProblemUpdateComponent implements OnInit {

  constructor(private problemService: LabProblemsService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  updateProblem(id : number, desc: string, score: number) {
    if(id==0 || desc=="" || score==0){
      console.log("null credentials");
      return;
    }
    this.problemService.updateProblem({
      id:id,
      description:desc,
      score:score
    })
      .subscribe(problem=>console.log("update problem", problem));
    this.location.back();
  }

  Number(value: string) {
    return Number(value);
  }
}
