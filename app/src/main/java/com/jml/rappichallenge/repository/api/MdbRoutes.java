package com.jml.rappichallenge.repository.api;

import com.jml.rappichallenge.models.enums.Language;
import com.jml.rappichallenge.models.enums.Sorting;

import androidx.annotation.StringRes;

class MdbRoutes
{
	static class Version
	{
		static final String V3 = "3";
	}

	static  class Path
	{
		static final String Discover = "discover";
		static final String Movie = "movie";
	}

	static class QKey
	{
		static final String ApiKey = "api_key";
		static final String Language = "language";
		static final String Sorting = "sort_by";
		static final String IncludeAdult = "include_adult";
		static final String IncludeVideo = "include_video";
		static final String Page = "page";
		static final String Keyword = "with_keywords";
	}

	static class LanguageCode
	{
		private static final String EN_code = "en-US";
		private static final String ES_code = "es-ES";

		static String fromLanguage(Language language)
		{
			switch (language)
			{
				case English:
					return EN_code;
				case Spanish:
					return ES_code;
				default:
						return EN_code;
			}
		}
	}

	static class SortingCode
	{
		private static final String PopulariteCode = "popularity.desc";
		private static final String RatingCode = "vote_average.desc";
		private static final String DateCode = "primary_release_date.desc";

		static String fromSorting(Sorting sorting)
		{
			switch (sorting)
			{
				case Popularity:
					return PopulariteCode;
				case Rating:
					return RatingCode;
				case Date:
					return DateCode;
				default:
					return PopulariteCode;
			}
		}
	}

	static final String RootUrl = "https://api.themoviedb.org/";
}
