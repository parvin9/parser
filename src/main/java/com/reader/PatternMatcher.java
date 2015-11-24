package com.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {

	public static String match(String document, String matchPattern) {

		Pattern pattern = Pattern.compile(matchPattern, Pattern.DOTALL);
		Matcher patternMatcher = pattern.matcher(document);

		if (patternMatcher.find()) {
			String matched = patternMatcher.group(1);

			return matched;
		}
		return null;
	}

	public static List<String> matches(String document, String matchPattern,
			int limit) {

		Pattern pattern = Pattern.compile(matchPattern, Pattern.DOTALL);
		Matcher patternMatcher = pattern.matcher(document);
		int i = 0;
		List<String> matched = new ArrayList<>();

		while (patternMatcher.find()) {

			if (i < limit) {
				matched.add(patternMatcher.group(1));
			}
			i++;
		}
		return matched;
	}
}
