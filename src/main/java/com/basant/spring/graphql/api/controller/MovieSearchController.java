package com.basant.spring.graphql.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basant.spring.graphql.api.datafetcher.MovieDataFetcher;
import com.basant.spring.graphql.api.datafetcher.allMoviesDataFetcher;
import com.basant.spring.graphql.api.entity.Movie;
import com.basant.spring.graphql.api.service.MovieService;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@RestController
@RequestMapping("/MovieWiki")
public class MovieSearchController {
	@Autowired
	private MovieService service;
	// load graphqls file
	@Value("classpath:movie.graphqls")
	private Resource schemaResource;
	@Autowired
	private allMoviesDataFetcher allMoviesDataFetcher;
	@Autowired
	private MovieDataFetcher movieDataFetcher;

	private GraphQL graphQL;

	// load schema at application start up
	@PostConstruct
	public void loadSchema() throws IOException {
		// get the schema
		File schemaFile = schemaResource.getFile();
		// parse schema
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		/*
		 * This dataFetcher first argument i.e "allMovies" or "movie" this name
		 * should be same with the field which u declare in your movie.graphqls
		 * in typeQuery section and one more things these 2 field name should be
		 * same which we are sending as part of request query from postman for
		 * Example : { allMovies{pass required field } }
		 */
		return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring -> typeWiring
				.dataFetcher("allMovies", allMoviesDataFetcher).dataFetcher("movie", movieDataFetcher)).build();
	}

	/* Get List of Movie with all the fields */
	@GetMapping("/moviesList")
	public List<Movie> getMovieList() {
		return service.findAllMovies();
	}

	/*
	 * Get List of Movie with required the fields using graphQL API provided by
	 * Face-book
	 */
	@PostMapping("/getAllMovies")
	public ResponseEntity<Object> getAllMovies(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	/*
	 * Get Movie by movie id
	 */
	@GetMapping("/search/{movieId}")
	public Movie getMovieInfo(@PathVariable String movieId) {
		return service.findMovieById(movieId);
	}

	/*
	 * Get Movie by movie id with desired Fields not all fields those presents
	 * in Movie Entity if not required
	 */
	@PostMapping("/getMovieById")
	public ResponseEntity<Object> getMovieById(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}
