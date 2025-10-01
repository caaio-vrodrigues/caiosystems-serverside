package com.example.caiosystems.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserClient {
	
	private static final String ERR_PASSWORD = "Invalid password lenght, minimum 8 characters required";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    
    @Column(name="username", nullable=false, unique=true)
    @NotBlank(message="Username cannot be blank")
    @Email(message="Invalid e-mail format")
    private String username;
    
	@Column(name="password", nullable=false)
	@Size(min=8, message=ERR_PASSWORD)
    @NotBlank(message="Password cannot be blank")
	private String password;
}
