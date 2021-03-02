package com.bookmyshow.bookmyshowapi.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.bookmyshow.bookmyshowapi.models.City;

public interface CityRepository extends JpaRepository<City, Long>{
	Optional<City> findByName(String name);
}
