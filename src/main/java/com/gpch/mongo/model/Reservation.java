package com.gpch.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@Document(collection = "reservations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
	
	@Transient
	public static final String SEQUENCE_NAME="users_sequence";
	//which means field marked with @transient is ignored by mapping framework
	// the field mapped  to any db column
	
    @Id
    private long id;
    
    @NotBlank(message="Please provide a FirstName.")
    private String firstName;
    @NotBlank(message="Please provide a LastName.")
    private String lastName;
    @NotNull
    @NotBlank(message="Please provide a Mobile Number.")
    private String mobile;
    
    //@DateTimeFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
    
    @Email
    @NotBlank(message="Please provide a EmailId.")
    private String emailId;
    
    @NotBlank(message="Please provide a State.")
    private String state;
    
    @NotBlank(message="Please provide a City.")
    private String city;
    
    
    
    
}
