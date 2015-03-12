package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.github.arkainnihx.comp1206.coursework.fractals.FractalType;
import com.github.arkainnihx.comp1206.coursework.fractals.Mandelbrot;

public class Fractal {

	private FractalType type;
	private boolean isJulia;
	private double realUB, realLB, imaginaryUB, imaginaryLB, widthConstant, heightConstant;
	private int imageWidth, imageHeight, order;
	private ColourSpace gradient;
	private boolean isBlackSet;
	private Complex juliaAnchor;
	
	public Fractal() {
		this(new Mandelbrot());
	}
	
	public Fractal(FractalType type) {
		this(type, false);
	}
	
	public Fractal(boolean isJulia) {
		this(new Mandelbrot(), isJulia);
	}
	
	public Fractal(FractalType type, boolean isJulia) {
		this(type, isJulia, 2d, -2d, 1.6d, -1.6d, 0d, 0d, 0, 0, 2, new ColourSpace(new Color(0, 7, 100), new Color(32, 107, 203),
				new Color(237, 255, 255), new Color(255, 170, 0), new Color(0, 2, 0)), true, new Complex());
	}
	
	public Fractal(FractalType type, boolean isJulia, double realUB, double realLB, double imaginaryUB, double imaginaryLB, double widthConstant,
			double heightConstant, int imageWidth, int imageHeight, int order, ColourSpace gradient, boolean blackSet,
			Complex juliaAnchor) {
		this.type = type;
		this.isJulia = isJulia;
		this.realUB = realUB;
		this.realLB = realLB;
		this.imaginaryUB = imaginaryUB;
		this.imaginaryLB = imaginaryLB;
		this.widthConstant = widthConstant;
		this.heightConstant = heightConstant;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.order = order;
		this.gradient = gradient;
		this.isBlackSet = blackSet;
		this.juliaAnchor = juliaAnchor;
	}
	
	public FractalType getType() {
		return type;
	}

	public void setType(FractalType type) {
		this.type = type;
	}

	public boolean isJulia() {
		return isJulia;
	}

	public void setJulia(boolean isJulia) {
		this.isJulia = isJulia;
	}

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
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Color getGradStartColour() {
		return gradient.getStartColour();
	}

	public Color getGradEndColour() {
		return gradient.getEndColour();
	}
	
	public ArrayList<Color> getColourList() {
		return gradient.getColourList();
	}
	
	public int getColourFrequencyModifier() {
		return gradient.getFrequencyModifier();
	}
	
	public void setColourFrequencyModifier(int frequencyModifier) {
		gradient.setFrequencyModifier(frequencyModifier);
	}
	
	public ColourSpace getGradient() {
		return gradient;
	}

	public boolean isBlackSet() {
		return isBlackSet;
	}

	public void setBlackSet(boolean blackSet) {
		this.isBlackSet = blackSet;
	}
	
	public void setGradient(Color... colours) {
		gradient = new ColourSpace(colours);
	}

	public Complex getJuliaAnchor() {
		return juliaAnchor;
	}

	public void setJuliaAnchor(Complex juliaAnchor) {
		this.juliaAnchor = juliaAnchor;
	}

	public Complex imagePointToComplex(int xPoint, int yPoint) {
		return new Complex(realLB + (widthConstant * xPoint), imaginaryUB - (heightConstant * yPoint));
	}
	
	public BufferedImage generate(int iterations) {
		if (imageWidth > 0 && imageHeight > 0) {
			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			int xCount = 0, yCount = 0;
			for (yCount = imageHeight - 1; yCount >= 0; yCount--) {
				for (xCount = 0; xCount < imageWidth; xCount++) {
					Complex mathsPoint = imagePointToComplex(xCount, yCount);
					Color point;
					if (isJulia) {
						point = point(mathsPoint, juliaAnchor, iterations);
					} else {
						point = point(mathsPoint, mathsPoint, iterations);
					}
					image.setRGB(xCount, yCount, point.getRGB());
				}
			}
			return image;
		}
		return null;
	}
	
	protected Color point(Complex z, Complex c, int iterations) {
		int count = 0;
		int escapeVal = (order < 0 ? 4 : Integer.MAX_VALUE);
		while (z.modulusSquared() <  escapeVal && count < iterations) {
			z = type.equation(z, c, order);
			count++;
		}
		return colourPoint(z, count, iterations);
	}

	protected Color colourPoint(Complex z, int n, int maxN) {
		if (n == maxN) {
			if (isBlackSet()) {
				return Color.BLACK;
			} else {
				return gradient.getStartColour();
			}
		}
		double nu = Math.log(Math.log(z.modulus())) / Math.log(Math.abs(order));
		double v = n + 1 - nu;
		return gradient.smoothColouring(v, maxN);
	}

	protected double pixelConstant(int pixels, double upperBound, double lowerBound) {
		return (upperBound - lowerBound) / pixels;
	}
	
}