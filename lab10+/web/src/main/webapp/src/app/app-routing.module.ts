import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StudentsComponent} from "./students/students.component";
import {StudentDetailComponent} from "./students/student-detail/student-detail.component";
import {ProblemsComponent} from "./problems/problems.component";
import {StudentNewComponent} from "./students/student-new/student-new.component";
import {StudentDeleteComponent} from "./students/student-delete/student-delete.component";
import {StudentUpdateComponent} from "./students/student-update/student-update.component";
import {ProblemSaveComponent} from "./problems/problem-save/problem-save.component";
import {ProblemDeleteComponent} from "./problems/problem-delete/problem-delete.component";
import {ProblemUpdateComponent} from "./problems/problem-update/problem-update.component";
import {StudentSortComponent} from "./students/student-sort/student-sort.component";
import {StudentFilterComponent} from "./students/student-filter/student-filter.component";
import {ProblemSortComponent} from "./problems/problem-sort/problem-sort.component";
import {ProblemFilterComponent} from "./problems/problem-filter/problem-filter.component";
import {AssignmentSaveComponent} from "./assignments/assignment-save/assignment-save.component";
import {AssignmentsComponent} from "./assignments/assignments.component";
import {AssignmentDeleteComponent} from "./assignments/assignment-delete/assignment-delete.component";
import {AssignmentUpdateComponent} from "./assignments/assignment-update/assignment-update.component";
import {AssignmentSortComponent} from "./assignments/assignment-sort/assignment-sort.component";
import {AssignmentFilterComponent} from "./assignments/assignment-filter/assignment-filter.component";


const routes: Routes = [
  // { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'students', component: StudentsComponent},
  {path: 'student/detail/:id', component: StudentDetailComponent},
  {path: 'student/new', component: StudentNewComponent},
  {path : 'student/delete', component: StudentDeleteComponent},
  {path : 'student/update', component: StudentUpdateComponent},
  {path: 'student/sort', component:StudentSortComponent},
  {path: 'student/filter', component:StudentFilterComponent},

  {path: 'problems', component: ProblemsComponent},
  {path: 'problem/save', component:ProblemSaveComponent},
  {path: 'problem/delete', component:ProblemDeleteComponent},
  {path: 'problem/update', component:ProblemUpdateComponent},
  {path: 'problem/sort', component:ProblemSortComponent},
  {path: 'problem/filter', component:ProblemFilterComponent},

  {path: 'assignments', component:AssignmentsComponent},
  {path: 'assignment/save', component:AssignmentSaveComponent},
  {path: 'assignment/delete', component:AssignmentDeleteComponent},
  {path: 'assignment/update', component:AssignmentUpdateComponent},
  {path: 'assignment/sort', component:AssignmentSortComponent},
  {path: 'assignment/filter', component:AssignmentFilterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
