package com.jml.rappichallenge.repository.api;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MdbApiImpl implements MdbApi
{
	private interface Service
	{
		@GET(MdbRoutes.Version.V3 + "/" + MdbRoutes.Path.Discover + "/" + MdbRoutes.Path.Movie)
		Call<Object> search(@Path(MdbRoutes.QKey.ApiKey) String apiKey,
							@Path(MdbRoutes.QKey.Language) String language,
							@Path(MdbRoutes.QKey.Sorting) String sorting,
							@Path(MdbRoutes.QKey.IncludeAdult) boolean includeAdult,
							@Path(MdbRoutes.QKey.IncludeVideo) boolean includeVideo,
							@Path(MdbRoutes.QKey.Page) int page,
							@Path(MdbRoutes.QKey.Keyword) String keyword);

	}

	private Service service;

	public MdbApiImpl()
	{
		this.service = createService();
	}

	private Service createService()
	{
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
				.addInterceptor(logging)
				.readTimeout(20, SECONDS)
				.connectTimeout(20, SECONDS);

		OkHttpClient okHttpClient = okHttpClientBuilder.build();

		GsonBuilder builder = new GsonBuilder();

		return new Retrofit.Builder()
				.baseUrl(MdbRoutes.RootUrl)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create(builder.create()))
				.build()
				.create(Service.class);
	}

	@Override
	public void discoverMovie()
	{

	}


}
