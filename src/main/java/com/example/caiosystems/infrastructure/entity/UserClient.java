package com.example.caiosystems.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="username")
@Getter
@Builder
@Entity
@Table(
	name="user_client",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {
				"username"
			},
			name="UK_user_client_userame"
		)
	}
)
public class UserClient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    
    @Column(
    	name="username", 
    	nullable=false, 
    	unique=true)
    private String username;
    
	@Column(
		name="password", 
		nullable=false)
	private String password;
}