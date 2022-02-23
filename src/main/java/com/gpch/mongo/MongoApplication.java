package com.gpch.mongo;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
public class MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

	/*
	 * @Bean public CommandLineRunner setup(ReservationService reservationService) {
	 * return (args) -> { log.info("Generating sample data"); Date date = new
	 * Date(); reservationService.deleteAllReservations(); List<String> reservations
	 * = Arrays.asList("Bob", "Peter", "John", "David");
	 * reservations.forEach(reservation ->
	 * reservationService.saveReservation(Reservation.builder()
	 * .firstName(reservation) .date(date) .build()) );
	 * 
	 * 
	 * reservationService.getAllReservations().forEach(reservation ->
	 * log.info("RESERVATION --> " + reservation.getFirstName() + " ID: " +
	 * reservation.getId())); }; }
	 */
    
    
    
}




