package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Julia extends Fractal {
	

	public BufferedImage generate(int imageWidth, int imageHeight, Point userSelectedPoint, int iterations) {
		double widthConstant = pixelConstant(imageWidth, realUB, realLB);
		double heightConstant = pixelConstant(imageHeight, imaginaryUB, imaginaryLB);
		Complex c = imagePointToComplex(widthConstant, heightConstant, userSelectedPoint);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		int xCount = 0, yCount = 0;
		for (yCount = 0; yCount < imageHeight; yCount++) {
			for (xCount = 0; xCount < imageWidth; xCount++) {
				Complex mathsPoint = new Complex(realLB + (widthConstant * xCount), imaginaryLB + (heightConstant * yCount));
				image.setRGB(xCount, yCount, point(c, mathsPoint, iterations).getRGB());
			}
		}
		return image;
	}
	
	private Color point(Complex c, Complex d, int iterations) {
		Complex z = new Complex(d.getReal(), d.getImaginary());
		int count = 0;
		while (Math.sqrt(z.modulusSquared()) < 2 && count < iterations) {
			z = z.square().add(c);
			count++;
		}
		return colourPoint(z, count, iterations);
	}
	
	private Complex imagePointToComplex(double widthConstant, double heightConstant, Point userSelectedPoint) {
		return new Complex(widthConstant * userSelectedPoint.getX(), heightConstant * userSelectedPoint.getY());
	}

}