package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Tier;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findAllByTier(Tier tier);
	Optional<Seat> findById(Long seatId);
	
}
