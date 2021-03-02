package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Long>{
	Optional<Screen> findByTheatreIdAndScreenNumber(Long theatreId,  Long screenNumber);
	List<Screen> findAllByTheatreId(Long theatreId);
}
