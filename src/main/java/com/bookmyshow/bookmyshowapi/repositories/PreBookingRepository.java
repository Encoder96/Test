package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.PreBooking;

public interface PreBookingRepository extends JpaRepository<PreBooking, Long>{
	Optional<PreBooking> findBySeatId(Long seatId);
	List<PreBooking> findAllByUserId(Long userId);
}
