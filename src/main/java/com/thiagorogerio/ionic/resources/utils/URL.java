package com.thiagorogerio.ionic.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author trogerio
 *
 */
public class URL {

	public static String decondeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s) {
		/*
		 * String [] vetor = s.split(","); List<Integer> list = new ArrayList<>(); for
		 * (int i=0; i<vetor.length; i++ ) { list.add(Integer.parseInt(vetor[i])); }
		 * return list;
		 */
		return Arrays.asList(s.split(",")).stream().map(string -> Integer.parseInt(string))
				.collect(Collectors.toList());
	}
}
