package com.bookmyshow.bookmyshowapi.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy/mm/dd, hh-mm-ss")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy/mm/dd, hh-mm-ss")
	private Date updatedAt;
}
