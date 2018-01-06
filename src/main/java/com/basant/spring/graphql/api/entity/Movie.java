package com.basant.spring.graphql.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Movie {
	@Id
	private String movieid;
	private String movieName;
	private String releaseDate;
	private String[] actors;
	private String director;
	private String producer;

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

}
