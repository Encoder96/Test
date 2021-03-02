package com.bookmyshow.bookmyshowapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmyshow.bookmyshowapi.models.Screen;
import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Theatre;
import com.bookmyshow.bookmyshowapi.models.Tier;

@Service
public class AddScreenAndSeatService {
	
	public List<Seat> addSeats(Tier tier, int rows, int cols) {
		List<Seat> listSeat = new ArrayList<>();
		for(int i=0; i<rows; i++) {
			String tmp = tier.getName() + i;
			for(int j=1; j<=cols; j++) {
				String name = tmp + j;
				Seat seat = new Seat(tier, name, false, (double)tier.getMultiplier()*tier.getScreen().getTheatre().getBasePrice());
				listSeat.add(seat);
			}
		}
		return listSeat;
	}
	
	public List<Screen> addScreens(Theatre theatre, int num) {
		List<Screen> screenList = new ArrayList<>();
		for(int i=1; i<=num; i++) {
			Screen screen = new Screen(theatre, (long)i);
			screenList.add(screen);
		}
		return screenList;	
	}
}
