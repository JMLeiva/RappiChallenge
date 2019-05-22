package com.jml.rappichallenge.repository.api;

class MdbRoutes
{
	class Version
	{
		static final String V3 = "3";
	}

	class Path
	{
		static final String Discover = "discover";
		static final String Movie = "movie";
	}

	class QKey
	{
		static final String ApiKey = "api_key";
		static final String Language = "language";
		static final String Sorting = "sort_by";
		static final String IncludeAdult = "include_adult";
		static final String IncludeVideo = "include_video";
		static final String Page = "page";
		static final String Keyword = "with_keywords";
	}

	static final String RootUrl = "https://api.themoviedb.org/";
}
