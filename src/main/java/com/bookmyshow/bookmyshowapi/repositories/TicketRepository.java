package com.bookmyshow.bookmyshowapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
