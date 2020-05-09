import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {LabProblem} from "./problem.model";
import {Student} from "../../students/shared/student.model";



@Injectable()
export class LabProblemsService {
  private problemsUrl = 'http://localhost:8080/api/problems';
  private pageSize = 3;

  constructor(private httpClient: HttpClient) {
  }

  getPageSize(): number{
    return this.pageSize;
  }

  getProblemsOnPage(pageNo: number):Observable<LabProblem[]>{
    const url = `${this.problemsUrl}/get-page/pageno=${pageNo},size=${this.pageSize}`;
    return this.httpClient.get<Array<LabProblem>>(url);
  }

  getProblems(): Observable<LabProblem[]> {
    return this.httpClient
      .get<Array<LabProblem>>(this.problemsUrl);
  }

  saveProblem(problem: LabProblem): Observable<LabProblem> {
    console.log("saveProblem", problem);

    return this.httpClient
      .post<LabProblem>(this.problemsUrl, problem);
  }

  updateProblem(problem:LabProblem): Observable<LabProblem> {
    const url = `${this.problemsUrl}/${problem.id}`;
    return this.httpClient
      .put<LabProblem>(url, problem);
  }

  deleteProblem(id: number): Observable<any> {
    const url = `${this.problemsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

  sortProblems() {
    const url=`${this.problemsUrl}/sort`;
    return this.httpClient.get<Array<LabProblem>>(url);
  }

  filterProblems(score: string) {
    const url=`${this.problemsUrl}/filter/${score}`;
    return this.httpClient.get<Array<LabProblem>>(url);
  }
}
