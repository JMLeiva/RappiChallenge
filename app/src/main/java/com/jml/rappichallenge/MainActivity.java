package com.jml.rappichallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jml.rappichallenge.models.entities.MovieDiscoverResponse;
import com.jml.rappichallenge.models.enums.Language;
import com.jml.rappichallenge.models.enums.Sorting;
import com.jml.rappichallenge.repository.api.ApiCallback;
import com.jml.rappichallenge.repository.api.MdbApi;
import com.jml.rappichallenge.repository.api.MdbApiImpl;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TEst
		MdbApi api = new MdbApiImpl();

		api.discoverMovie(Language.English, Sorting.Popularity, 1, null, new ApiCallback<MovieDiscoverResponse>()
		{
			@Override
			public void onResponse(MovieDiscoverResponse response)
			{
				Log.i("TEST", response.toString());
			}

			@Override
			public void onFailure(int code, String message)
			{
				Log.i("TEST", message);
			}
		});
	}
}
