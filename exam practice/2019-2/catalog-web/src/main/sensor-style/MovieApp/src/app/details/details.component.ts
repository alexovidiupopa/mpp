import {Component, Input, OnInit} from '@angular/core';
import {Actor, Movie} from "../models";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {MovieService} from "../movie-service.service";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  @Input() movie:Movie
  addPosibility=false;
  selectedActor: Actor;
  availableActors:  Actor[];
  constructor(private movieService: MovieService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.movieService.getMovie(+params['id'])))
      .subscribe(movie => this.movie = movie);
  }

  addActors():void
  {
    this.addPosibility =true;
    this.movieService.getAvailableActors().subscribe(
       actors => this.availableActors = actors
     );
  }

  addActor() {
    console.log(this.selectedActor)
    this.movie.actors.push(this.selectedActor)
    this.availableActors=this.availableActors.filter(casting=>casting.id!==this.selectedActor.id);
    this.movieService.addActor(this.movie.id,this.selectedActor.id).subscribe(
      movie=>this.movie=movie
    );


  }

  something(selectedActor: Actor) {
    this.selectedActor=selectedActor;
  }
}
