package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Show;

public interface ShowRepository extends JpaRepository<Show, Long>{
	List<Show> findAllByCityIdAndIsAvailableForBooking(String cityId, Boolean bookingAvailable);
	List<Show> findAllByCityIdAndMovieNameAndIsAvailableForBooking(Long cityId, String movieName, Boolean bookingAvailable);
}
