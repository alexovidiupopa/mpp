import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SearchComponent} from "./search/search.component";
import {DetailsComponent} from "./details/details.component";


const routes: Routes = [
  {path: 'movies',component : SearchComponent},
  {path: 'movie/detail/:id', component: DetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
