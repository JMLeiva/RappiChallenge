package com.jml.rappichallenge.models.entities;

import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jml.rappichallenge.models.tools.LanguageHelper;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie
{
	private static class DTO
	{
		private int vote_count;
		private int id;
		private boolean video;
		private float vote_average;
		private String title;
		private float popularity;
		private String poster_path;
		private String backdrop_path;
		private String original_language;
		private String original_title;
		private List<Namable> genres;
		private boolean adult;
		private String overview;
		private String release_date;
		private int budget;
		private int revenue;
		private String homepage;
		private String imdbId;
		private int runtime;
		private List<Namable> production_companies; // TODO <- DTO mas completo tiene imagen
		private List<Namable> production_countries;
		private List<Namable> spoken_languages;
		private String status;
		private String tagline;
	}

	private DTO dto;

	Movie(DTO dto)
	{
		this.dto = dto;
	}

	public int getId()
	{
		return dto.id;
	}

	public boolean hasVideo()
	{
		return dto.video;
	}

	public float getVoteCount()
	{
		return dto.vote_count;
	}

	public float getVoteAverage()
	{
		return dto.vote_average;
	}

	public String getTitle()
	{
		return dto.title;
	}

	public float getPopularity()
	{
		return dto.popularity;
	}

	public String getPosterPath()
	{
		return dto.poster_path;
	}

	public String getBackdropPath()
	{
		return dto.backdrop_path;
	}

	public String getOriginalLanguageCode()
	{
		return dto.original_language;
	}

	public String getOriginalTitle()
	{
		return dto.original_title;
	}


	// TODO Generos
	//private List<Integer> genre_ids;

	public boolean isAdult()
	{
		return dto.adult;
	}

	public String getOverview()
	{
		return dto.overview;
	}

	public Date getReleaseDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		try
		{
			return dateFormat.parse(dto.release_date);
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	public List<Namable> getGenres()
	{
		return new ArrayList<>(dto.genres);
	}

	public int getBudget()
	{
		return dto.budget;
	}

	public int getRevenue()
	{
		return dto.revenue;
	}

	public String getHomepage()
	{
		return dto.homepage;
	}

	public String getImdbId()
	{
		return dto.imdbId;
	}

	public int getRuntime()
	{
		return dto.runtime;
	}

	public List<Namable> getProductionCompanies()
	{
		return new ArrayList<>(dto.production_companies);
	}

	public List<Namable> getProductionCountries()
	{
		return new ArrayList<>(dto.production_countries);
	}

	public List<Namable> getSpokenLanguages()
	{
		return new ArrayList<>(dto.spoken_languages);
	}

	public String getStatus()
	{
		return dto.status;
	}

	public String getTagline()
	{
		return dto.tagline;
	}

	public static class Deserializer implements JsonDeserializer<Movie>
	{
		@Override
		public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Movie.DTO dto = context.deserialize(json, Movie.DTO.class);
			return new Movie(dto);
		}
	}

	public static class FormatHelper
	{
		public static String getFormmatedLanguage(Context context, String languageCode)
		{
			int id = LanguageHelper.GetLanguageResNameByCode(languageCode);

			if(id == 0)
			{
				return "";
			}

			return context.getString(id);
		}
	}
}
