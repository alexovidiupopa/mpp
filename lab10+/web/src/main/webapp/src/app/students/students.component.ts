import {Component} from "@angular/core";
import {StudentService} from "./shared/student.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'ubb-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css'],
})
export class StudentsComponent {
  constructor(private router: Router) {
  }

  addNewStudent() {
    console.log("add new student btn clicked ");

    this.router.navigate(["student/new"]);
  }

  deleteStudent() {
    console.log("delete student btn clicked");

    this.router.navigate(["student/delete"]);
  }

  updateStudent() {
    console.log("update student btn clicked");

    this.router.navigate(["student/update"]);
  }

  filterStudents() {
    console.log("filter students btn clicked");

    this.router.navigate(["student/filter"]);
  }

  sortStudents() {
    console.log("sort students btn clicked");

    this.router.navigate(["student/sort"]);

  }
}
