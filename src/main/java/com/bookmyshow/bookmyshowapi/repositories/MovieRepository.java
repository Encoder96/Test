package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.enums.Language;
import com.bookmyshow.bookmyshowapi.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	Optional<Movie> findByMovieNameAndLanguage(String movieName, Language language);
	List<Movie> findAllByMovieName(String movieName);
}
