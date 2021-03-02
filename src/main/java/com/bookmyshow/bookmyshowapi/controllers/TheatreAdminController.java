package com.bookmyshow.bookmyshowapi.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bookmyshowapi.exceptions.CityNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.MovieNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.ScreenNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.TheatreNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.TierNotFoundException;
import com.bookmyshow.bookmyshowapi.models.City;
import com.bookmyshow.bookmyshowapi.models.Movie;
import com.bookmyshow.bookmyshowapi.models.Screen;
import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Show;
import com.bookmyshow.bookmyshowapi.models.Theatre;
import com.bookmyshow.bookmyshowapi.models.Tier;
import com.bookmyshow.bookmyshowapi.repositories.CityRepository;
import com.bookmyshow.bookmyshowapi.repositories.MovieRepository;
import com.bookmyshow.bookmyshowapi.repositories.ScreenRepository;
import com.bookmyshow.bookmyshowapi.repositories.SeatRepository;
import com.bookmyshow.bookmyshowapi.repositories.ShowRepository;
import com.bookmyshow.bookmyshowapi.repositories.TheatreRepository;
import com.bookmyshow.bookmyshowapi.repositories.TierRepository;
import com.bookmyshow.bookmyshowapi.services.AddScreenAndSeatService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/theatreAdmin")
public class TheatreAdminController {

	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private AddScreenAndSeatService addScreenAndSeatService;
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private TierRepository tierRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ShowRepository showRepository;
	
	@PostMapping("/addScreen/{theatreId}/{screens}")
	public String addScreen(@PathVariable(name = "theatreId") Long theatreId,
							@PathVariable(name = "screens") int screens) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		log.info("request to add screen in theatre " + theatreId);
		if(!theatreOptional.isPresent()) {
			throw new TheatreNotFoundException("theatre not found with mentioned id " + theatreId);
		}
		List<Screen> screenList = addScreenAndSeatService.addScreens(theatreOptional.get(), screens);
		screenRepository.saveAll(screenList);
		return "screen added";
	}
	
	@PostMapping("/addTier/{screenId}")
	public String addTier(@PathVariable(name = "screenId") Long screenId,
						@RequestBody Tier tier) {
		Optional<Screen> screenObject = screenRepository.findById(screenId);
		log.info("Request to add more Tiers");
		if(!screenObject.isPresent()) {
			throw new ScreenNotFoundException("screen not added");
		}
		Tier tierObject = Tier.builder().
				screen(screenObject.get()).
				name(tier.getName()).
				multiplier(tier.getMultiplier()).
				build();
		tierRepository.save(tierObject);
		return "Tier added with id# " + tierObject.getId();						
	}
	
	@PostMapping("/addSeats/{tierId}")
	public String addSeats(@PathVariable(name = "tierId") Long tierId,
							@RequestParam(name = "rows") int rows,
							@RequestParam(name = "cols") int cols) {
		log.info("Request to add seats to tier# " + tierId);
		Optional<Tier> tierOptional = tierRepository.findById(tierId);
		if(!tierOptional.isPresent()) {
			throw new TierNotFoundException("tier with id# " + tierId + " not found");
		}
		List<Seat> listSeat = addScreenAndSeatService.addSeats(tierOptional.get(), rows, cols);
		seatRepository.saveAll(listSeat);
		return rows*cols + " added to the tier id# " + tierId;
	}
	
	@RequestMapping("/addShow/{theatreId}")
	public String addShows(@PathVariable(name = "theatreId") Long theatreId,
							@RequestParam(name = "cityId") Long cityId,
							@RequestParam(name = "screenId") Long screenId,
							@RequestParam(name = "movieId") Long movieId,
							@RequestParam(name = "showTime") @DateTimeFormat(pattern = "yyyy-mm-dd, hh-mm-ss") Date showTiming) {
		Optional<City> cityOptional = cityRepository.findById(cityId);
		log.info("Request to add show for movie#" + movieId);
		if(!cityOptional.isPresent()) {
			throw new CityNotFoundException("city not found");
		}
		Optional<Screen> screenOptional = screenRepository.findByTheatreIdAndScreenNumber(theatreId, screenId);
		if(!screenOptional.isPresent()) {
			throw new ScreenNotFoundException("Screen not found");
		}
		
		Optional<Movie> movieOptional = movieRepository.findById(movieId);
		if(!movieOptional.isPresent()) {
			throw new MovieNotFoundException("movie not found");
		}
		
		Show show = Show.builder().
				screen(screenOptional.get()).
				isAvailableForBooking(true).
				movie(movieOptional.get()).
				showTime(showTiming).
				build();
		showRepository.save(show);
		return "show added";
	}

	@GetMapping("/getScreens/{theatreId}")
	public List<Screen> getScreens(@PathVariable(name = "theatreId") Long theatreId) {
		log.info("List all the screens in theatre# " + theatreId);
		return screenRepository.findAllByTheatreId(theatreId);
	}
}