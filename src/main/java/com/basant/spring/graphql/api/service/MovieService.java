package com.basant.spring.graphql.api.service;

import java.util.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basant.spring.graphql.api.entity.Movie;
import com.basant.spring.graphql.api.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private MovieRepository repository;

	@PostConstruct
	public void initMovies() {
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie("M17TZ", "Tiger Zinda Hai",
				"22-12-2017", new String[] { "Salman Khan", "Katrina Kaif", "Sajjad Delfrooz", "Angad Bedi",
						"Kumud Mishra", "Nawab Shah", "Girish Karnad", "Paresh" },
				"Ali Abbas Zafar", "Yash Raj Films"));
		movies.add(new Movie("M17BTC", "Baahubali 2 The Conclusion", "28-04-2017",
				new String[] { "Prabhas", "Sudeep", "Anushka Shetty", "Rana Daggubati", "Tamannaah", "Satyaraj",
						"Ramya Krishnan", "Nassar", "Shriya Saran", "Rohini" },
				"S.S.Rajamouli", "Shobu Yarlagadda and Prasad Devineni"));
		repository.save(movies);
	}

	public List<Movie> findAllMovies() {
		return repository.findAll();
	}

	public Movie findMovieById(String movieId) {
		return repository.findOne(movieId);
	}

}
