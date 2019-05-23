package com.jml.rappichallenge.repository.api;

import com.jml.rappichallenge.models.entities.MovieDiscoverResponse;
import com.jml.rappichallenge.models.enums.Language;
import com.jml.rappichallenge.models.enums.Sorting;


public interface MdbApi
{
	void discoverMovie(Language language, Sorting sorting, int page, String keyword,
					   final ApiCallback<MovieDiscoverResponse> callback);
}
