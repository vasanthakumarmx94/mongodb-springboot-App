package com.gpch.mongo.controller;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.service.ReservationService;
import com.gpch.mongo.service.SequenceGeneratorService;
import com.neosoft.mongo.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ReservationThymeleafController {

	private static final Logger log=LoggerFactory.getLogger(ReservationThymeleafController.class);
	
	
    private ReservationService reservationService;

    @Autowired
    public ReservationThymeleafController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
	private SequenceGeneratorService sequenceGeneratorService;
    
    @GetMapping("/reservations-home")
    public String reservationshome() {
        return "admindashboard";
    }
    
    
    @GetMapping("/reservations-ui")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        log.info("GET:/reservations-ui--->Called to get All");
        return "reservations";
    }
    
    @GetMapping("/contact-us")
    public String contactUs() {
        log.info("GET://contact-u--->Called contactUs");
        return "contactus";
    }

    @GetMapping("/delete-reservation/{id}")
    public String removeReservation(@PathVariable("id") Long id, Model model) {
        reservationService.deleteReservationById(id);
        model.addAttribute("reservations", reservationService.getAllReservations());
        log.info("DELETE:/delete-reservation/{"+id+"}--->Called");
        return "reservations";
    }

    @GetMapping(value = {"/edit-add-reservation/{id}", "/edit-add-reservation"})
    public String editReservation(@PathVariable("id") Optional<Long> id, Model model) {
        Reservation reservation = id.isPresent() ?
                reservationService.findReservationById(id.get()).get() : new Reservation();
        model.addAttribute("reservation", reservation);
        return "add-edit";
    }
    
    @PostMapping("/save-reservation")
    public String editReservation(@ModelAttribute("reservation") @Valid Reservation reservation,
                                  BindingResult bindingResult) throws ResourceNotFoundException {
        if (bindingResult.hasErrors()) {
            return "add-edit";
        }
        if(reservation.getId()==0) {
        	reservation.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
            log.info("POST:/save-reservation/{"+reservation.getId()+"}-->Called");
            reservationService.saveReservation(reservation);
            return "redirect:reservations-ui";	
        }else {
        	log.info("PUT:/save-reservation/{"+reservation.getId()+"}-->Called to Update");
        	reservationRepository.findById(reservation.getId())
        	.orElseThrow(() -> new ResourceNotFoundException("Reservation user not found  for id:" +reservation.getId() ));
        	reservation.setId(reservation.getId());
    	    reservationService.saveReservation(reservation);
    		return "redirect:reservations-ui";
        } 
    }
    
    //@GetMapping("/","/search")
    @RequestMapping(path = {"/","/search"})
    public String searchByfNameOrlNameOrMailOrMobile(String keyword,Model model) {
        model.addAttribute("reservations", reservationService.getAllReservationsBySearch(keyword));
        log.info("GET:/search/{"+keyword+"}--->Called to get seached recors");
        return "reservations";
    }
    
}





