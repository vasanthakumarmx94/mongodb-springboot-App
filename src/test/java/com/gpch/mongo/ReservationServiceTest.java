package com.gpch.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.service.ReservationService;
import com.neosoft.mongo.exception.ResourceNotFoundException;

import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.BDDMockito.given;

//@ContextConfiguration(classes = MockitoApplicationTest.class)
@SpringBootTest
@Rollback(false)
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private ReservationService reservationService;

	@Autowired
	private MockMvc mvc;

	@After
	public void tearDown() {

	}

	@Test
	public void reservationSaveTest() {
		Reservation reservation = new Reservation();
		long userId = 1L;
		reservation.setId(userId);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
		// assertEquals(reservation,
		// reservationService.findReservationById(reservation.getId()));

		when(reservationRepository.save(ArgumentMatchers.any(Reservation.class))).thenReturn(reservation);
		Reservation created = reservationService.saveReservation(reservation);
		assertThat(created.getFirstName()).isSameAs(reservation.getFirstName());
		verify(reservationRepository).save(reservation);
	}

	@Test
	public void whenGivenId_shouldDeleteUser_ifFound() {
		Reservation reservation = new Reservation();
		reservation.setId(1L);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
		when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
		reservationService.deleteReservationById(reservation.getId());
		verify(reservationRepository).deleteById(reservation.getId());
	}

	@Test(expected = RuntimeException.class)
	public void should_throw_exception_when_user_doesnt_exist() {
		Reservation reservation = new Reservation();
		reservation.setId(81L);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
//			doReturn(Optional.of(reservation)).when(reservationRepository).findById(reservation.getId());
//			assertNotNull(reservation,"Reservation with Id : "+reservation.getId()+" not found");
//		    assertEquals(value,reservation.getId());

		given(reservationRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(null));
		reservationService.deleteReservationById(reservation.getId());
		//Assume.assumeTrue(false);
	}

	@Test
	public void shouldReturnAllUsers() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(new Reservation());
		given(reservationRepository.findAll()).willReturn(reservations);
		List<Reservation> expected = (List<Reservation>) reservationService.getAllReservations();
		assertEquals(expected, reservations);
		verify(reservationRepository).findAll();

	}

	@Test
	public void whenGivenId_shouldUpdateUser_ifFound() {
		Reservation reservation = new Reservation();
		reservation.setId(81L);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
		Reservation newreservation = new Reservation();
		newreservation.setId(reservation.getId());
		newreservation.setFirstName("Vasu");
		newreservation.setFirstName("Vasantha");
		newreservation.setLastName("kumar");
		newreservation.setMobile("1234567890");
		newreservation.setDate(LocalDateTime.now());
		newreservation.setEmailId("vasanthakumar@gmail.com");
		newreservation.setState("karnataka");
		newreservation.setCity("Tumakur");
		given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(newreservation));
		reservationService.saveReservation(newreservation);
		verify(reservationRepository).save(newreservation);
		// verify(reservationRepository).findById(newreservation.getId());

	}

	@Test(expected = RuntimeException.class)
	public void should_throw_exception_when_usernot_exist() {
		Reservation reservation = new Reservation();
		reservation.setId(81L);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
		Reservation newreservation = new Reservation();
		newreservation.setId(90L);
		newreservation.setFirstName("New Test Name");
		given(reservationRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(null));
		reservationService.saveReservation(newreservation);
		Assume.assumeTrue(false);
	}

	@Test
	public void findById_ifFound() {
		Reservation reservation = new Reservation();
		reservation.setId(81L);
		reservation.setFirstName("Vasantha");
		reservation.setLastName("kumar");
		reservation.setMobile("1234567890");
		reservation.setDate(LocalDateTime.now());
		reservation.setEmailId("vasanthakumar@gmail.com");
		reservation.setState("karnataka");
		reservation.setCity("Tumakur");
		when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
		Reservation expected = reservationService.findReservationById(reservation.getId()).get();
		assertThat(expected).isSameAs(reservation);
		verify(reservationRepository).findById(reservation.getId());
	}

	


	
	
	
	
	

}
