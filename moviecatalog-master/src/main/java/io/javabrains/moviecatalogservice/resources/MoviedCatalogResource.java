package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;
import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MoviedCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


        /* List<Rating> ratings= Arrays.asList(
                 new Rating("1234",4),
                 new Rating("5678",3)*/

        UserRating userRating =restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users/"+userId, UserRating.class);

        //UserRating userRating =restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);


         return userRating.getUserRating().stream().map(rating -> {
            Movie movie =restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/"+rating.getMovieId(), Movie.class);


            /* Movie movie=webClientBuilder.build().get()
                     .uri("http://localhost:8082/movies/"+rating.getMovieId())
                     .retrieve()
                     .bodyToMono(Movie.class).block();
*/
             return new CatalogItem(movie.getName(),movie.getDesc(),rating.getRating());
            }).collect(Collectors.toList());

    }
}
