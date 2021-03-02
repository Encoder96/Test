package com.bookmyshow.bookmyshowapi.controllers;

import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.exceptions.*;
import org.springframework.web.bind.annotation.*;
import com.bookmyshow.bookmyshowapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@PostMapping("/addCity")
	public String addCity(@RequestBody City city) {
			Optional<City> cityOptional = cityRepository.findByName(city.getName());
			log.info("request recieved to add new city with name " + city.getName());
			if(cityOptional.isPresent()) {
				log.info("City " + city.getName() + " already present");				
				throw new CityAlreadyPresentException("City with name " + city.getName() + " is already present");
			}
			City cityObject = City.builder().
					name(city.getName()).
					state(city.getState()).
					country(city.getCountry()).
					build();
			cityRepository.save(cityObject);					
		return "city " + cityObject.getName() + " with id" + cityObject.getId() + " is added";
	}
	
	@GetMapping("/getCities")
	public List<City> getCities() {
		log.info("Request to get all the added cities");
		return cityRepository.findAll();
	}
	
	@GetMapping("/getCity/{cityName}")
	public City getCity(@PathVariable(name = "cityName") String name) {
		Optional<City>	optionalCity = cityRepository.findByName(name);
		log.info("Search request for City " + name);
		if(!optionalCity.isPresent()) {			
			throw new CityNotFoundException(name + " citi not found");
		}
		return optionalCity.get();
	}

	@GetMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		log.info("Request for all users");
		return users;
	}
	
	@PostMapping("/addTheatre")
	public String addTheatre(@RequestParam(name = "cityName") String cityName, @RequestBody Theatre theatre) {
		Optional<City> optionalCity = cityRepository.findByName(cityName);
		log.info("Request to add theatre with name " + theatre.getName() + " in location " + theatre.getLocationName());
		if(!optionalCity.isPresent()) {
			throw new CityNotFoundException("No city found with name " + cityName);
		}
		
		Optional<Theatre> optionalTheatre = theatreRepository.findByNameAndLocationName(theatre.getName(), theatre.getLocationName());
		if(optionalTheatre.isPresent()) {
			throw new TheatreAlreadyPresentException("Theatre - " + theatre.getName() + " already present");
		}
		Location location;
		Optional<Location> optionalLocation = locationRepository.findByLatitudeAndLongitude(theatre.getLocation().getLatitude(), theatre.getLocation().getLongitude());
		if(!optionalLocation.isPresent()) {
			location = new Location(theatre.getLocation().getLatitude(), theatre.getLocation().getLongitude());
			locationRepository.save(location);
		}	
		else {
			location = optionalLocation.get();
		}
		
		Theatre theatreObject = Theatre.builder().
				city(optionalCity.get()).
				location(location).
				locationName(theatre.getLocationName()).
				name(theatre.getName()).
				basePrice(theatre.getBasePrice()).
				build();
		theatreRepository.save(theatreObject);
		return "Theatre with Id" + theatreObject.getId();
	}
	
	@PostMapping("/addMovie")
	public String addMovie(@RequestBody Movie movie) {
		log.info("Request to add new movie : " + movie.getMovieName());
		Optional<Movie> optionalMovie = movieRepository.findByMovieNameAndLanguage(movie.getMovieName(), movie.getLanguage());
		if(optionalMovie.isPresent()) {
			throw new MovieAlreadyPresentException("Movie already present");
		}			
		Movie movieObject = Movie.builder().
				movieName(movie.getMovieName()).
				aboutMovie(movie.getAboutMovie()).
				language(movie.getLanguage()).
				genre(movie.getGenre()).
				certificateType(movie.getCertificateType()).
				build();
		movieRepository.save(movieObject);
		return "Movie with name " + movie.getMovieName() + " is added";
	}
}