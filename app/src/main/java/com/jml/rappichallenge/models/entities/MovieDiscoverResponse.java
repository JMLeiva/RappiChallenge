package com.jml.rappichallenge.models.entities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieDiscoverResponse
{
	private static class DTO
	{
		private int page;
		private int total_results;
		private int total_pages;
		private List<Movie> results;
	}

	private DTO dto;

	MovieDiscoverResponse(DTO dto)
	{
		this.dto = dto;
	}

	public int getPage()
	{
		return dto.page;
	}

	public int getTotalResultCount()
	{
		return dto.total_results;
	}

	public int getPagesCount()
	{
		return dto.total_pages;
	}

	public List<Movie> getResult()
	{
		return new ArrayList<>(dto.results);
	}

	public static class Deserializer implements JsonDeserializer<MovieDiscoverResponse>
	{
		@Override
		public MovieDiscoverResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			MovieDiscoverResponse.DTO dto = context.deserialize(json, MovieDiscoverResponse.DTO.class);
			return new MovieDiscoverResponse(dto);
		}
	}
}
