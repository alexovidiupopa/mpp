import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Assignment} from "./assignment.model";


@Injectable()
export class AssignmentService {
  private assignmentsUrl = 'http://localhost:8080/api/assignments';
  private pageSize=2;
  constructor(private httpClient: HttpClient) {
  }

  getAssignmentsPaginated(pageNo:number):Observable<Assignment[]>{
    const url=`${this.assignmentsUrl}/get-page/pageno=${pageNo},size=${this.pageSize}`
    return this.httpClient.get<Array<Assignment>>(url);
  }

  getAssignments(): Observable<Assignment[]> {
    return this.httpClient
      .get<Array<Assignment>>(this.assignmentsUrl);
  }


  saveAssignment(problem: Assignment): Observable<Assignment> {
    console.log("saveProblem", problem);

    return this.httpClient
      .post<Assignment>(this.assignmentsUrl, problem);
  }

  updateAssignment(problem:Assignment): Observable<Assignment> {
    const url = `${this.assignmentsUrl}/${problem.id.first}/${problem.id.second}`;
    return this.httpClient
      .put<Assignment>(url, problem);
  }

  deleteAssignment(sid: number, pid:number): Observable<any> {
    const url = `${this.assignmentsUrl}/${sid}/${pid}`;
    return this.httpClient
      .delete(url);
  }

  sortAssignments() {
    const url=`${this.assignmentsUrl}/sort`;
    return this.httpClient.get<Array<Assignment>>(url);
  }

  filterAssignments(grade: string) {
    const url=`${this.assignmentsUrl}/filter/${grade}`;
    return this.httpClient.get<Array<Assignment>>(url);
  }

  getPageSize() {
    return this.pageSize;
  }
}
