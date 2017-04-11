package edu.demo.utils;

public class StringUtils {
	public static String upperCaseFirstLetter(String word) {
		StringBuilder sbd = new StringBuilder(word);
		String firstLetter = sbd.substring(0, 1);
		sbd.replace(0, 1, firstLetter.toUpperCase());
		return sbd.toString();
	}

	public static String lowerCaseFirstLetter(String word) {
		StringBuilder sbd = new StringBuilder(word);
		String firstLetter = sbd.substring(0, 1);
		sbd.replace(0, 1, firstLetter.toLowerCase());
		return sbd.toString();
	}

	public static String camelCase(String word) {
		return camelCase(word, true);
	}

	public static String camelCase(String word, boolean lowerCaseFirstLetter) {
		String[] parts = word.split("_");

		StringBuilder builder = new StringBuilder();

		for (String part : parts) {
			builder.append(upperCaseFirstLetter(part));
		}
		if (lowerCaseFirstLetter) {
			return lowerCaseFirstLetter(builder.toString());
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		String s = "creat_time_now";

		System.out.println(s.substring(s.indexOf("_") + 1));
	}
}
