package com.shivumytri.correlationtest.main;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.opencsv.CSVReader;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		String fileName = "C:\\Users\\i329330\\Downloads\\Cor_ParkinsonDisease.csv"; // provide ur location of the file
																						// name;

		List<Feature> lstOfCorlat = App.readDataLineByLine(fileName);

		System.out.println(lstOfCorlat.toString());

		boolean isBinaryMethod = false; // true -- running in binary method mode false -- running in fibonnaci mode.

		double numberofFeatures = 500; // increase number of features when running in fiboncci mode.

		double sqrtofNoFeatures = Math.sqrt(numberofFeatures);

		System.out.println(sqrtofNoFeatures);

		Comparator<Feature> comp = (feature1, feature2) -> feature1.getAverageValue() > feature2.getAverageValue() ? 1
				: (feature1.getAverageValue() < feature2.getAverageValue() ? -1 : 0);

		lstOfCorlat.sort(comp);

		System.out.println(lstOfCorlat.toString());

		int bucketSize = 3;
		boolean first = true;
		List<List<Feature>> result = new ArrayList<List<Feature>>();
		List<List<Map<Integer, List<Feature>>>> result2 = new ArrayList<List<Map<Integer, List<Feature>>>>();

		int lastIndex = 0;
		boolean ascending = true;

		for (long i = 0; i < sqrtofNoFeatures; i++) {
			int binaryResult = 0;
			if (isBinaryMethod) {
				binaryResult = (int) Math.round(Math.pow(2, i));
			} else {
				binaryResult = (int) fib(i);
			}

			if (ascending) {
				for (int j = 0; j < bucketSize; j++) {

					int lowerLimit;
					int upperLimit;

					if (first) {
						List<Feature> temp = new ArrayList<Feature>();
						result.add(temp);

						List<Map<Integer, List<Feature>>> temp2 = new ArrayList<Map<Integer, List<Feature>>>();
						Map<Integer, List<Feature>> maptest = new TreeMap<Integer, List<Feature>>();
						temp2.add(maptest);
						result2.add(temp2);
					}

					lowerLimit = lastIndex;
					upperLimit = lastIndex + binaryResult;

					result.get(j).addAll(getCurFeature(lowerLimit, upperLimit, lstOfCorlat));

					List<Feature> elements = new ArrayList<Feature>();
					elements.addAll(getCurFeature(lowerLimit, upperLimit, lstOfCorlat));

					result2.get(j).get(0).put(binaryResult, elements);

					lastIndex = upperLimit;
				}

			} else {
				for (int j = bucketSize - 1; j >= 0; j--) {

					int lowerLimit;
					int upperLimit;

					if (first) {
						List<Feature> temp = new ArrayList<Feature>();
						result.add(temp);

						List<Map<Integer, List<Feature>>> temp2 = new ArrayList<Map<Integer, List<Feature>>>();
						Map<Integer, List<Feature>> maptest = new TreeMap<Integer, List<Feature>>();
						temp2.add(maptest);
						result2.add(temp2);
					}

					lowerLimit = lastIndex;
					upperLimit = lastIndex + binaryResult;

					result.get(j).addAll(getCurFeature(lowerLimit, upperLimit, lstOfCorlat));

					List<Feature> elements = new ArrayList<Feature>();
					elements.addAll(getCurFeature(lowerLimit, upperLimit, lstOfCorlat));

					result2.get(j).get(0).put(binaryResult, elements);

					lastIndex = upperLimit;
				}

			}

			ascending = ascending ? false : true;
			first = false;
		}
		int j = 0;
		for (List<Map<Integer, List<Feature>>> temp : result2) {
			System.out.println("======Bucket number=======" + j);
			for (Entry<Integer, List<Feature>> curr : temp.get(0).entrySet()) {
				if (!curr.getValue().isEmpty()) {
					for (Feature f1test : curr.getValue()) {
						System.out.print(f1test.getFeatureName()+" ");
					}
				}
			}
			System.out.println();

			j = j + 1;
		}
	}

	private static List<Feature> getCurFeature(int lowerLimt, int upperLimt, List<Feature> lstOfCorlat) {

		List<Feature> temp = new ArrayList<Feature>();

		for (int i = lowerLimt; i < upperLimt; i++) {
			try {
				temp.add(lstOfCorlat.get(i));
			} catch (IndexOutOfBoundsException ex) {
				break;
			}
		}

		return temp;
	}

	// Java code to illustrate reading a
	// CSV file line by line
	public static List<Feature> readDataLineByLine(String file) {

		List<Feature> lstOfCorlat = new ArrayList<Feature>();

		try {

			// Create an object of filereader
			// class with CSV file as a parameter.
			FileReader filereader = new FileReader(file);

			// create csvReader object passing
			// file reader as a parameter
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;

			// we are going to read data line by line
			int i = 0;
			while ((nextRecord = csvReader.readNext()) != null) {

				if (nextRecord[0].equals("Dataset")) {
					for (String cell : nextRecord) {
						System.out.print(cell + "\t");
						if (!cell.equals("Dataset")) {
							Feature f1 = new Feature();
							f1.setFeatureName(cell);
							lstOfCorlat.add(f1);
						}
					}
					System.out.println();
				}

				if (nextRecord[0].equals("Average")) {
					for (String cell : nextRecord) {
						System.out.print(cell + "\t");
						if (!cell.equals("Average")) {
							Feature f1 = lstOfCorlat.get(i);
							f1.setAverageValue(Double.parseDouble(cell));
							i += 1;
						}
					}
					System.out.println();
				}

			}
			System.out.println(lstOfCorlat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstOfCorlat;
	}

	static long fib(long n) {

		int a = 0, b = 1, c;
		if (n == 0)
			return a;
		for (int i = 2; i <= n; i++) {
			c = a + b;
			a = b;
			b = c;
		}
		return b;
	}

}
