package com.bookmyshow.bookmyshowapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bookmyshowapi.exceptions.CityNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.PaymentFailedException;
import com.bookmyshow.bookmyshowapi.exceptions.ShowNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.ShowStartedExceptions;
import com.bookmyshow.bookmyshowapi.exceptions.SomethingWentWrongExceptions;
import com.bookmyshow.bookmyshowapi.exceptions.UserNotFoundException;
import com.bookmyshow.bookmyshowapi.models.City;
import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.models.PreBooking;
import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Show;
import com.bookmyshow.bookmyshowapi.models.Theatre;
import com.bookmyshow.bookmyshowapi.repositories.BookingRepository;
import com.bookmyshow.bookmyshowapi.repositories.CityRepository;
import com.bookmyshow.bookmyshowapi.repositories.PreBookingRepository;
import com.bookmyshow.bookmyshowapi.repositories.SeatRepository;
import com.bookmyshow.bookmyshowapi.repositories.ShowRepository;
import com.bookmyshow.bookmyshowapi.repositories.TheatreRepository;
import com.bookmyshow.bookmyshowapi.repositories.TicketRepository;
import com.bookmyshow.bookmyshowapi.repositories.UserRepository;
import com.bookmyshow.bookmyshowapi.services.DisplaySeatService;
import com.bookmyshow.bookmyshowapi.services.PaymentService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private ShowRepository showRepository;
	
	@Autowired
	private DisplaySeatService displaySeatService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PreBookingRepository preBookingRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BookingRepository bookingRepository;	
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping("{cityName}/getTheatres/")
	public List<Theatre> getTheatresByCity(@PathVariable(name = "cityName") String cityName) {
		log.info("Requets to list theatres in city " + cityName);
		Optional<City> cityOptional = cityRepository.findByName(cityName);
		if(!cityOptional.isPresent()) {
			throw new CityNotFoundException(cityName + " city not found");
		}
		return theatreRepository.findAllByCity(cityOptional.get());
	}
	
	@GetMapping("{cityName}/getShows/")
	public List<Show> getShows(@PathVariable(name = "cityName") String cityName) {
		log.info("get all shows in city " + cityName);
		Optional<City> cityOptional = cityRepository.findByName(cityName);
		if(!cityOptional.isPresent()) {
			throw new CityNotFoundException(cityName + " city not found");
		}
		List<Show> listShows = showRepository.findAllByCityIdAndIsAvailableForBooking(cityName, true);
		if(listShows.isEmpty()) {
			throw new ShowNotFoundException("show not found");
		}
		return listShows;
	}
	
	@GetMapping("{cityId}/{movieName}/")
	public List<Show> getShowsByMovieName(@PathVariable(name = "cityId") Long cityId, 
											@PathVariable(name = "movieName") String movieName) {
		log.info("Find all shows with movie name " + movieName);
		List<Show> listShows = showRepository.findAllByCityIdAndMovieNameAndIsAvailableForBooking(cityId, movieName,true);
		if(listShows.isEmpty()) {
			throw new ShowNotFoundException("show not found");
		}
		return listShows;
	}
	
	@GetMapping("/getTheatres/theatreId")
	public Theatre getTheatre(@PathVariable(name = "theatreId") Long theatreId) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		log.info("find theatre by id " + theatreId);
		if(!theatreOptional.isPresent()) {
			throw new ShowNotFoundException("show not found");
		}
		return theatreOptional.get();
	}
	
	@GetMapping("/getSeats")
	public List<Seat> getSeats(@PathVariable(name = "showId") Long showId) {
		Optional<Show> showOptional = showRepository.findById(showId);
		log.info("find available seats for show " + showId);
		if(!showOptional.isPresent()) {
			throw new ShowNotFoundException("show not found");
		}
		Long screenId = showOptional.get().getScreen().getId();
		return displaySeatService.getAvailableSeats(screenId);
	}
	
	@PostMapping("/bookSeats/{showId}")
	public String bookSeats(@PathVariable(name = "showId") Long showId, 
							@RequestParam(name = "mobile") String mobile,
							@RequestBody List<Long> seatId) {
		log.info("book seats for mobile user " + mobile + "for show " + showId);
		Optional<Show> showOptional = showRepository.findById(showId);
		if(!showOptional.isPresent()) {
			throw new ShowNotFoundException("show not found");
		}
		
		if(!showOptional.get().isAvailableForBooking()) {
			throw new ShowStartedExceptions("show started, ticket window closed");
		}
		
		Optional<User> userOptional = userRepository.findByMobile(mobile);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user with mobile# " + mobile + " not found");
		}
		List<PreBooking> preBooking = new ArrayList();
		
		for(Long id : seatId) {
			Optional<PreBooking> bookings = preBookingRepository.findBySeatId(id);
			if(bookings.isPresent() || seatRepository.findById(id).get().isBooked()) {
				throw new SomethingWentWrongExceptions("seat may be already locked");
			}
			PreBooking pre = PreBooking.builder().
							 user(userOptional.get()).
							 seat(seatRepository.findById(id).get()).
							 show(showOptional.get()).
							 build();
			preBooking.add(pre);
		}
		preBookingRepository.saveAll(preBooking);
		return "make payment to block seats";		
	}
	
	@GetMapping("/payment/{userId}")
	public Ticket paymentCheckout(@PathVariable(name = "userId") Long userId) {
		log.info("make payment for the user# " + userId);
		Optional<User> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user not found with id# " + userId);			
		}
		List<PreBooking> preBooking = preBookingRepository.findAllByUserId(userId);
		if(preBooking.isEmpty()) {
			throw new SomethingWentWrongExceptions("something went wrong, please try booking again");
		}
		double payAmt = preBooking.get(0).getSeat().getSeatPrice();
		if(!paymentService.pay(payAmt*preBooking.size())) {
			preBookingRepository.deleteAll(preBooking);
			throw new PaymentFailedException("payment failed");
		}
		List<String> seats = new ArrayList<>();
		for(PreBooking preBook : preBooking) {
			preBook.getSeat().setBooked(true);
			seats.add(preBook.getSeat().getName());
		}
		
		Ticket ticket = Ticket.builder().
						show(preBooking.get(0).getShow()).
						amount(payAmt*preBooking.size()).
						seats(seats).
						build();
		Booking booking = Booking.builder().
						 user(userOptional.get()).
						 ticket(ticket).build();
		
		ticketRepository.save(ticket);
		preBookingRepository.deleteAll(preBooking);
		bookingRepository.save(booking);
		return ticket;
	}
	
	@GetMapping("/getBookings/{userId}")
    public List<Booking> getBookings(@PathVariable(name = "userId") Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        log.info("Request received to fetch all booking for id - "+userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with Id - "+userId+" not found");
        }
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);

        return bookings;
    }	
}
