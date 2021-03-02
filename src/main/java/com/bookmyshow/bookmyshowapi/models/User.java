package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends Auditable{
	@Column(name = "name", nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String mobile;
}
