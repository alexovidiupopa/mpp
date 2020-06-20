export class Movie{
  title:string
  year:number
  id: number
  actors: Actor[]=new Array<Actor>()
}

export class Actor{
  name:string
  rating: number
  id:number
}
