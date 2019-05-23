package com.jml.rappichallenge.repository.api;

import com.google.gson.GsonBuilder;
import com.jml.rappichallenge.BuildConfig;
import com.jml.rappichallenge.models.entities.Movie;
import com.jml.rappichallenge.models.entities.MovieDiscoverResponse;
import com.jml.rappichallenge.models.entities.Namable;
import com.jml.rappichallenge.models.enums.Language;
import com.jml.rappichallenge.models.enums.Sorting;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MdbApiImpl implements MdbApi
{
	private interface Service
	{
		@GET(MdbRoutes.Version.V3 + "/" + MdbRoutes.Path.Discover + "/" + MdbRoutes.Path.Movie)
		Call<MovieDiscoverResponse> search(@Query(MdbRoutes.QKey.ApiKey) String apiKey,
										   @Query(MdbRoutes.QKey.Language) String language,
										   @Query(MdbRoutes.QKey.Sorting) String sorting,
										   @Query(MdbRoutes.QKey.IncludeAdult) boolean includeAdult,
										   @Query(MdbRoutes.QKey.IncludeVideo) boolean includeVideo,
										   @Query(MdbRoutes.QKey.Page) int page,
										   @Query(MdbRoutes.QKey.Keyword) String keyword);

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

		GsonBuilder builder = new GsonBuilder()
				.registerTypeAdapter(Namable.class, new Namable.Deserializer())
				.registerTypeAdapter(Movie.class, new Movie.Deserializer())
				.registerTypeAdapter(MovieDiscoverResponse.class, new MovieDiscoverResponse.Deserializer());

		return new Retrofit.Builder()
				.baseUrl(MdbRoutes.RootUrl)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create(builder.create()))
				.build()
				.create(Service.class);
	}

	@Override
	public void discoverMovie(Language language, Sorting sorting, int page, String keyword,
							  final ApiCallback<MovieDiscoverResponse> callback)
	{
		Call<MovieDiscoverResponse> call = service.search(BuildConfig.ApiKey,
				MdbRoutes.LanguageCode.fromLanguage(language),
				MdbRoutes.SortingCode.fromSorting(sorting),
				false,
				true, page, keyword);

		makeCall(call, callback);
	}

	private<T> void makeCall(Call<T> call, final ApiCallback<T> callback)
	{
		call.enqueue(new Callback<T>()
		{
			@Override
			public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response)
			{
				if(response.isSuccessful())
				{
					callback.onResponse(response.body());
				}
				else
				{
					String errorMessage = "";

					try
					{
						ResponseBody errprResponse = response.errorBody();

						if(errprResponse != null)
						{
							errorMessage = errprResponse.string();
						}
					}
					catch (IOException e)
					{
						// IGNORE
					}

					callback.onFailure(response.code(), errorMessage);
				}
			}

			@Override
			public void onFailure(@NonNull Call<T> call, @NonNull Throwable t)
			{
				callback.onFailure(0, t.getMessage());
			}
		});
	}
}
