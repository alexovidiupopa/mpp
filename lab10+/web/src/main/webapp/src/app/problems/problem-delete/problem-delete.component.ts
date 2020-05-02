import { Component, OnInit } from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-problem-delete',
  templateUrl: './problem-delete.component.html',
  styleUrls: ['./problem-delete.component.css']
})
export class ProblemDeleteComponent implements OnInit {

  constructor(private problemService: LabProblemsService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  deleteProblem(id: number){
    console.log("delete problem btn clicked");
    this.problemService.deleteProblem(id)
      .subscribe(problem=>console.log("deleted problem ", problem));
    this.location.back();
  }

  Number(value: string) {
    return Number(value);
  }
}
