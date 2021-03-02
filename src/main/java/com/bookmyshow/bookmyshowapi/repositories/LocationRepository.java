package com.bookmyshow.bookmyshowapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{
	Optional<Location> findByLatitudeAndLongitude(String Latitude, String Longitude);
}
