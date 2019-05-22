package com.jml.rappichallenge.models.tools;

import com.jml.rappichallenge.R;

import androidx.annotation.StringRes;

public class LanguageHelper
{
	public static @StringRes int GetLanguageResNameByCode(String code)
	{
		// TODO usar locale para esto?
		switch (code)
		{
			case "en":
				return R.string.lan_english;
			default:
				return 0;
		}
	}
}
