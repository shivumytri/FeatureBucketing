package com.shivumytri.correlationtest.main;

public class Feature {

	private String featureName;
	private double averageValue;

	public Feature() {
		super();
	}

	public Feature(String className, double averageValue) {
		super();
		this.featureName = className;
		this.averageValue = averageValue;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String className) {
		this.featureName = className;
	}

	public double getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(double averageValue) {
		this.averageValue = averageValue;
	}

	@Override
	public String toString() {
		// return "[ " + this.getFeatureName() + " : " + this.getAverageValue() + " ]";

		return this.getFeatureName();
	}

}
