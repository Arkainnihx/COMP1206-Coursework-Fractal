package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;

public abstract class Fractal {

	protected double realUB = 2d, realLB = -2d, imaginaryUB = 1.6d, imaginaryLB = -1.6d;
	protected ColourSpace gradient = new ColourSpace(Color.WHITE, Color.BLACK);
	protected boolean blackSet = false;

	public double getRealUB() {
		return realUB;
	}

	public void setRealUB(double realUB) {
		this.realUB = realUB;
	}

	public double getRealLB() {
		return realLB;
	}

	public void setRealLB(double realLB) {
		this.realLB = realLB;
	}

	public double getImaginaryUB() {
		return imaginaryUB;
	}

	public void setImaginaryUB(double imaginaryUB) {
		this.imaginaryUB = imaginaryUB;
	}

	public double getImaginaryLB() {
		return imaginaryLB;
	}

	public void setImaginaryLB(double imaginaryLB) {
		this.imaginaryLB = imaginaryLB;
	}

	public Color getGradStartPoint() {
		return gradient.getStartPoint();
	}

	public Color getGradEndPoint() {
		return gradient.getEndPoint();
	}

	public void setGradient(Color startPoint, Color endPoint) {
		this.gradient = new ColourSpace(startPoint, endPoint);
	}

	public boolean isBlackSet() {
		return blackSet;
	}

	public void setBlackSet(boolean blackSet) {
		this.blackSet = blackSet;
	}

	protected Color colourPoint(Complex z, int n, int maxN) {
		if (n == maxN) {
			if (isBlackSet()) {
				return Color.BLACK;
			} else {
				return gradient.getStartPoint();
			}
		}
		float v = (float) (1 - ((n + 1 - Math.log(Math.log(Math.sqrt(z.modulusSquared())) / Math.log(2)) / Math.log(2)) / maxN));
		return gradient.generateColour(v);
	}

	protected double pixelConstant(int pixels, double upperBound, double lowerBound) {
		return (upperBound - lowerBound) / pixels;
	}

}