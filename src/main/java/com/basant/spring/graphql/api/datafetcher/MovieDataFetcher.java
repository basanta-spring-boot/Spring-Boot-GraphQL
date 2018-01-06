package com.basant.spring.graphql.api.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basant.spring.graphql.api.entity.Movie;
import com.basant.spring.graphql.api.repository.MovieRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class MovieDataFetcher implements DataFetcher<Movie> {
	@Autowired
	private MovieRepository repository;

	@Override
	public Movie get(DataFetchingEnvironment environment) {
		String movieId = environment.getArgument("id");
		return repository.findOne(movieId);
	}

}
