import {Component, OnInit} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";


@Component({
  moduleId: module.id,
  selector: 'ubb-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css'],
})
export class StudentListComponent implements OnInit {
  errorMessage: string;
  students: Array<Student>;
  selectedStudent: Student;
  currentPage: number;
  totalSize:number;
  acceptableSize:number;

  constructor(private studentService: StudentService,
              private router: Router) {

  }

  ngOnInit(): void {
    this.currentPage=0;
    this.studentService.getStudents()
      .subscribe(
        students=>this.totalSize=students.length,
        error=>this.errorMessage=<any>error);
    this.getStudentsPaginated();
  }


  getStudentsPaginated(){
    this.studentService.getStudentsOnPage(this.currentPage)
      .subscribe(students=>this.students = students,
                error=>this.errorMessage=<any>error);
  }

  getStudents() {
    this.studentService.getStudents()
      .subscribe(
        students => this.students = students,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(student: Student): void {
    this.selectedStudent = student;
  }

  gotoDetail(): void {
    this.router.navigate(['/student/detail', this.selectedStudent.id]);
  }

  deleteStudent(student: Student) {
    console.log("deleting student: ", student);

    this.studentService.deleteStudent(student.id)
      .subscribe(_ => {
        console.log("student deleted");

        this.students = this.students
          .filter(s => s.id !== student.id);
      });
  }

  increasePageNo() {
    this.acceptableSize=Math.ceil(this.totalSize/this.studentService.getPageSize().valueOf());
    if (this.currentPage<this.acceptableSize-1){
      this.currentPage++;
      this.getStudentsPaginated();
    }
  }

  decreasePageNo() {
    if(this.currentPage>0) {
      this.currentPage--;
      this.getStudentsPaginated();
    }
  }
}
