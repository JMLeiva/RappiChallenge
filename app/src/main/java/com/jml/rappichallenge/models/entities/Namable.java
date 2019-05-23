package com.jml.rappichallenge.models.entities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Namable
{
	private static class DTO
	{
		private String name;
	}

	private DTO dto;

	Namable(DTO dto)
	{
		this.dto = dto;
	}

	public String getName()
	{
		return dto.name;
	}

	public static class Deserializer implements JsonDeserializer<Namable>
	{
		@Override
		public Namable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Namable.DTO dto = context.deserialize(json, Namable.DTO.class);
			return new Namable(dto);
		}
	}
}
