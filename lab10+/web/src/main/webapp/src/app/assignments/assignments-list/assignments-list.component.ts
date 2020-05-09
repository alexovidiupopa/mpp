import { Component, OnInit } from '@angular/core';
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list.component.html',
  styleUrls: ['./assignments-list.component.css']
})
export class AssignmentsListComponent implements OnInit {
  assignments: Assignment[];
  errorMessage: string;
  currentPage: number;
  totalSize:number;
  acceptableSize:number;
  constructor(private assignmentService: AssignmentService) { }

  ngOnInit(): void {
    this.currentPage=0;
    this.assignmentService.getAssignments()
      .subscribe(
        assg=>this.totalSize=assg.length,
        error=>this.errorMessage=<any>error);
    this.getAssignmentsPaginated();
  }

  increasePageNo() {
    this.acceptableSize=Math.ceil(this.totalSize/this.assignmentService.getPageSize().valueOf());
    if (this.currentPage<this.acceptableSize-1){
      this.currentPage++;
      this.getAssignmentsPaginated();
    }
  }

  decreasePageNo() {
    if(this.currentPage>0) {
      this.currentPage--;
      this.getAssignmentsPaginated();
    }
  }

  private getAssignmentsPaginated() {
    this.assignmentService.getAssignmentsPaginated(this.currentPage)
      .subscribe(assig=>this.assignments = assig,
        error=>this.errorMessage=<any>error);
  }
}
