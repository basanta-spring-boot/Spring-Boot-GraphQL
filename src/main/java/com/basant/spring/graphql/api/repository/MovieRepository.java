package com.basant.spring.graphql.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basant.spring.graphql.api.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {

}
