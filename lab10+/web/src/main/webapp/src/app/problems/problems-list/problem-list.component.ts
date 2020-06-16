import {Component, OnInit} from '@angular/core';
import {LabProblemsService} from "../shared/problems.service";
import {LabProblem} from "../shared/problem.model";

@Component({
  selector: 'app-discipline-list',
  templateUrl: './problem-list.component.html',
  styleUrls: ['./problem-list.component.css']
})
export class ProblemListComponent implements OnInit {
  errorMessage: string;
  problems: LabProblem[];
  currentPage: number;
  totalSize:number;
  acceptableSize:number;

  constructor(private labProblemsService: LabProblemsService) {

  }

  ngOnInit(): void {
    this.currentPage=0;
    this.labProblemsService.getProblems()
      .subscribe(
        problems=>this.totalSize=problems.length,
        error=>this.errorMessage=<any>error);
    this.getProblemsPaginated();
  }

  buttonsInteraction(){
    this.acceptableSize=Math.ceil(this.totalSize/this.labProblemsService.getPageSize().valueOf());
    if (this.currentPage==0)
      document.getElementById("decrease-btn").hidden=true;
    else if (this.currentPage==this.acceptableSize-1)
      document.getElementById("increase-btn").hidden=true;
  }

  getProblemsPaginated(){
    this.buttonsInteraction();
    this.labProblemsService.getProblemsOnPage(this.currentPage)
      .subscribe(problems=>this.problems = problems,
        error=>this.errorMessage=<any>error);
  }

  increasePageNo() {
    this.acceptableSize=Math.ceil(this.totalSize/this.labProblemsService.getPageSize().valueOf());
    if (this.currentPage<this.acceptableSize-1){
      this.currentPage++;
      document.getElementById("decrease-btn").hidden=false;
      this.getProblemsPaginated();
    }

  }

  decreasePageNo() {
    if(this.currentPage>0) {
      this.currentPage--;
      document.getElementById("increase-btn").hidden=false;
      this.getProblemsPaginated();
    }
  }
}
