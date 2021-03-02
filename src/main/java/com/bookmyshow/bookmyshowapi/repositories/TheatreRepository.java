package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.City;
import com.bookmyshow.bookmyshowapi.models.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Long>{
	Optional<Theatre> findByNameAndLocationName(String theatreName, String locationName); 
	List<Theatre> findAllByCity(City city);
}
