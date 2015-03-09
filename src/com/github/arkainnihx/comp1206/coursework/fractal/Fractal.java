package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;

public abstract class Fractal {

	protected double realUB = 2d, realLB = -2d, imaginaryUB = 1.6d, imaginaryLB = -1.6d, widthConstant = 0d, heightConstant = 0d;
	protected ColourSpace gradient = new ColourSpace(Color.BLACK, Color.ORANGE, Color.WHITE, Color.BLUE, new Color(0, 0, 64));
	protected boolean blackSet = false;

	public double getRealUB() {
		return realUB;
	}

	public double getRealLB() {
		return realLB;
	}

	public double getImaginaryUB() {
		return imaginaryUB;
	}

	public double getImaginaryLB() {
		return imaginaryLB;
	}
	
	public void setBounds(double realUB, double realLB, double imaginaryUB, double imaginaryLB) {
		this.realUB = realUB;
		this.realLB = realLB;
		this.imaginaryUB = imaginaryUB;
		this.imaginaryLB = imaginaryLB;
	}
	
	public double getWidthConstant() {
		return widthConstant;
	}

	public double getHeightConstant() {
		return heightConstant;
	}

	public void setConversionConstants(int imageWidth, int imageHeight) {
		widthConstant = pixelConstant(imageWidth, realUB, realLB);
		heightConstant = pixelConstant(imageHeight, imaginaryUB, imaginaryLB);
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
	
	public Complex imagePointToComplex(int xPoint, int yPoint) {
		return new Complex(realLB + (widthConstant * xPoint), imaginaryUB - (heightConstant * yPoint));
	}

	protected Color colourPoint(Complex z, int n, int maxN) {
		if (n == maxN) {
			if (isBlackSet()) {
				return Color.BLACK;
			} else {
				return gradient.getStartPoint();
			}
		}
		float v = (float) (1 - (n + 1 - Math.log(Math.log(Math.sqrt(z.modulusSquared())) / Math.log(2)) / Math.log(1000)) / maxN);
		return gradient.generateColour(v);
	}

	protected double pixelConstant(int pixels, double upperBound, double lowerBound) {
		return (upperBound - lowerBound) / pixels;
	}

}