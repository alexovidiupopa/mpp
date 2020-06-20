import { Component, OnInit } from '@angular/core';
import {MovieService} from "../movie-service.service";
import {Router} from "@angular/router";
import {Movie} from "../models"

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;
  NoSearch:boolean;
  selectedMovie: Movie;
  title:string;
  //selectedClient: Client;

  constructor(private movieService: MovieService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
      this.NoSearch=false;
  }

  filterMovies(title: string) {
    console.log("filter movies", title);
    this.NoSearch=true
    this.movieService.filterMovies().subscribe(
      movies => this.movies = movies.filter(movie=>movie.title.includes(title)),
      error => this.errorMessage = <any>error
    );

  }
  //
  // filterClientsAge(age: string) {
  //   console.log("filter clients", age);
  //
  //   this.clientService.filterClientsAge(age).subscribe(
  //     clients => this.clients = clients,
  //     error => this.errorMessage = <any>error
  //   );
  //
  // }
  // filterClientsFirstName(name: string) {
  //   console.log("filter clients", name);
  //
  //   this.clientService.filterClientsFirstName(name).subscribe(
  //     clients => this.clients = clients,
  //     error => this.errorMessage = <any>error
  //   );
  //
  // }
  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
  //
  // goBack(): void {
  //
  //   this.location.back();
  // }
  //
  gotoDetail(): void {
    console.log("here 2")
    this.router.navigate(['/movie/detail', this.selectedMovie.id]);
  }
  //
  // deleteClient(client: Client) {
  //   console.log("deleting client: ", client);
  //
  //   this.clientService.deleteClient(client.id)
  //     .subscribe(_ => {
  //       console.log("client deleted");
  //
  //       this.clients = this.clients
  //         .filter(s => s.id !== client.id);
  //     });
  // }


}
