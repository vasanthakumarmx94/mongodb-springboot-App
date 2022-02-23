package com.gpch.mongo.service;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }
    
    public Iterable<Reservation> getAllReservationsBySearch(String searchstr){
        return reservationRepository.searchByfNameOrlNameOrMailOrMobile(searchstr);
    }
    
    public void deleteAllReservations(){
        reservationRepository.deleteAll();
    }

    public void deleteReservationById(Long id){
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> findReservationById(Long id){
        return reservationRepository.findById(id);
    }
}
