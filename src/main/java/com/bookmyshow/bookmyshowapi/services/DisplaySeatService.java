package com.bookmyshow.bookmyshowapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Tier;
import com.bookmyshow.bookmyshowapi.repositories.ScreenRepository;
import com.bookmyshow.bookmyshowapi.repositories.SeatRepository;
import com.bookmyshow.bookmyshowapi.repositories.TierRepository;

@Service
public class DisplaySeatService {
	
	@Autowired
	private TierRepository tierRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	public List<Seat> getAvailableSeats(Long screenId) {
		List<Tier> tierList = tierRepository.findAllByScreenId(screenId);
		List<Seat> seatList = new ArrayList();
		
		for(Tier tier : tierList) {
			List<Seat> seats = seatRepository.findAllByTier(tier);
			for(Seat s : seats) {
				if(!s.isBooked()) 
					seatList.add(s);				
			}
		}
		return seatList;
	}
}
