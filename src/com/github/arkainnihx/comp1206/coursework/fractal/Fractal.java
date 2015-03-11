package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Fractal {

	protected double realUB = 2d, realLB = -2d, imaginaryUB = 1.6d, imaginaryLB = -1.6d, widthConstant = 0d, heightConstant = 0d;
	protected int imageWidth = 0, imageHeight = 0;
	protected ColourSpace gradient = new ColourSpace(new Color(128, 0, 64), new Color(0, 64, 0), new Color(0, 0, 64));
	protected boolean blackSet = true;

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
		calculateConstants();
	}

	public void setImageSize(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		calculateConstants();
	}

	public double getWidthConstant() {
		return widthConstant;
	}

	public double getHeightConstant() {
		return heightConstant;
	}

	private void calculateConstants() {
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
		double nu = Math.log(Math.log(z.modulus())) / Math.log(2);
		double v = n + 1 - nu;
		return gradient.smoothColouring(v, maxN);
	}

	protected double pixelConstant(int pixels, double upperBound, double lowerBound) {
		return (upperBound - lowerBound) / pixels;
	}

	public abstract BufferedImage generate(int iterations);

}