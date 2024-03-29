package io.javabrains.movieinfoservice.resources;

import io.javabrains.movieinfoservice.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @RequestMapping("/{movieId}")
  public Movie getMovieInfo(@PathVariable("movieId") String  movieId){
      return new Movie(movieId,"Test desc");
  }

}
