import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {StudentDetailComponent} from "./students/student-detail/student-detail.component";
import {StudentsComponent} from "./students/students.component";
import {StudentListComponent} from "./students/student-list/student-list.component";
import {StudentService} from "./students/shared/student.service";
import { ProblemsComponent } from './problems/problems.component';
import { ProblemListComponent } from './problems/problems-list/problem-list.component';
import {LabProblemsService} from "./problems/shared/problems.service";
import { StudentNewComponent } from './students/student-new/student-new.component';
import { StudentDeleteComponent } from './students/student-delete/student-delete.component';
import { StudentUpdateComponent } from './students/student-update/student-update.component';
import { ProblemDeleteComponent } from './problems/problem-delete/problem-delete.component';
import { ProblemUpdateComponent } from './problems/problem-update/problem-update.component';
import { ProblemSaveComponent } from './problems/problem-save/problem-save.component';
import { StudentFilterComponent } from './students/student-filter/student-filter.component';
import { StudentSortComponent } from './students/student-sort/student-sort.component';
import { ProblemSortComponent } from './problems/problem-sort/problem-sort.component';
import {ProblemFilterComponent} from "./problems/problem-filter/problem-filter.component";
import { AssignmentsComponent } from './assignments/assignments.component';
import { AssignmentDeleteComponent } from './assignments/assignment-delete/assignment-delete.component';
import { AssignmentSaveComponent } from './assignments/assignment-save/assignment-save.component';
import { AssignmentUpdateComponent } from './assignments/assignment-update/assignment-update.component';
import { AssignmentsListComponent } from './assignments/assignments-list/assignments-list.component';
import { AssignmentSortComponent } from './assignments/assignment-sort/assignment-sort.component';
import { AssignmentFilterComponent } from './assignments/assignment-filter/assignment-filter.component';
import {AssignmentService} from "./assignments/shared/assignment.service";

@NgModule({
  declarations: [
    AppComponent,
    StudentDetailComponent,
    StudentsComponent,
    StudentListComponent,
    ProblemsComponent,
    ProblemListComponent,
    StudentNewComponent,
    StudentDeleteComponent,
    StudentUpdateComponent,
    ProblemDeleteComponent,
    ProblemUpdateComponent,
    ProblemSaveComponent,
    StudentFilterComponent,
    StudentSortComponent,
    ProblemSortComponent,
    ProblemFilterComponent,
    AssignmentsComponent,
    AssignmentDeleteComponent,
    AssignmentSaveComponent,
    AssignmentUpdateComponent,
    AssignmentsListComponent,
    AssignmentSortComponent,
    AssignmentFilterComponent,


  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [StudentService, LabProblemsService, AssignmentService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
