package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bookmyshow.bookmyshowapi.enums.CertificateType;
import com.bookmyshow.bookmyshowapi.enums.Genre;
import com.bookmyshow.bookmyshowapi.enums.Language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movies")
public class Movie extends Auditable{

	@Column(name = "movieName", nullable = false)
	private String movieName;
	
	@Column(nullable = false)
	private String aboutMovie;
	
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Enumerated(EnumType.STRING)
	private Genre genre;
	
	@Enumerated(EnumType.STRING)	
	private CertificateType certificateType;
}
