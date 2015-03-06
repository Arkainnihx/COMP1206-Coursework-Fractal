package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {

	public BufferedImage generate(int imageWidth, int imageHeight, int iterations) {
		double widthConstant = pixelConstant(imageWidth, realUB, realLB);
		double heightConstant = pixelConstant(imageHeight, imaginaryUB, imaginaryLB);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		int xCount = 0, yCount = 0;
		for (yCount = 0; yCount < imageHeight; yCount++) {
			for (xCount = 0; xCount < imageWidth; xCount++) {
				Complex mathsPoint = new Complex(realLB + (widthConstant * xCount), imaginaryLB + (heightConstant * yCount));
				image.setRGB(xCount, yCount, point(mathsPoint, iterations).getRGB());
			}
		}
		return image;
	}

	private Color point(Complex c, int iterations) {
		Complex z = new Complex(c.getReal(), c.getImaginary());
		int count = 0;
		while (Math.sqrt(z.modulusSquared()) < 2 && count < iterations) {
			z = z.square().add(c);
			count++;
		}
		return colourPoint(z, count, iterations);
	}

}