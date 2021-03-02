package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "locations")
public class Location extends Auditable{
	private String latitude;
	private String longitude;
}
