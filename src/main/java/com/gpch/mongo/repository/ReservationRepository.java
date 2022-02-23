package com.gpch.mongo.repository;

import com.gpch.mongo.model.Reservation;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	
	//@Query("{$or:[{firstName : ?0},{lastName:?0},{emailId:?0},{mobile:?0}]}")
	
	@Query("{$or:[{firstName : ?0},{lastName:?0},{emailId:?0},{mobile:?0}]}")
	List<Reservation> searchByfNameOrlNameOrMailOrMobile( String searchtext);
	
	
}
