package ba.edu.ibu.movieswatchlist.rest.controllers;

import ba.edu.ibu.movieswatchlist.core.model.Genre;
import ba.edu.ibu.movieswatchlist.core.model.Movie;
import ba.edu.ibu.movieswatchlist.core.model.User;
import ba.edu.ibu.movieswatchlist.core.service.GenreService;
import ba.edu.ibu.movieswatchlist.core.service.MovieService;
import ba.edu.ibu.movieswatchlist.rest.dto.MovieDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/{userId}")
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDTO movieDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO, userId));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Movie>> getMoviesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(movieService.getMoviesByUserSortedByTitle(userId));
    }

    @GetMapping("/sort/watchlist/user/{userId}")
    public ResponseEntity<List<Movie>> sortMoviesByWatchlistOrder(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "asc") String order) {
        return ResponseEntity.ok(movieService.sortMoviesByWatchlistOrder(userId, order));
    }



    @GetMapping("/filter/status/user/{userId}/")
    public ResponseEntity<List<Movie>> filterMoviesByStatus(
            @PathVariable Long userId,
            @RequestParam String status) {
        return ResponseEntity.ok(movieService.filterMoviesByStatus(userId, status));
    }


    @GetMapping("/filter/watchlist/user/{userId}")
    public ResponseEntity<List<Movie>> filterMoviesByWatchlistOrder(
            @PathVariable Long userId,
            @RequestParam String order) {
        return ResponseEntity.ok(movieService.filterMoviesByWatchlistOrder(userId, order));
    }


    @GetMapping("/filter/genre/user/{userId}")
    public ResponseEntity<List<Movie>> filterMoviesByGenre(
            @PathVariable Long userId,
            @RequestParam String genreName) {
        return ResponseEntity.ok(movieService.filterMoviesByGenre(userId, genreName));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> editMovie(
            @PathVariable Long movieId,
            @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.editMovie(movieId, movieDTO));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}