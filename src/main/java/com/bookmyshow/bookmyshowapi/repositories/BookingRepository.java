package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findAllByUserId(Long userId);
}
