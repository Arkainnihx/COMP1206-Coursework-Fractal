package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {

	public BufferedImage generate(int iterations) {
		if (imageWidth > 0 && imageHeight > 0) {
			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			int xCount = 0, yCount = 0;
			for (yCount = imageHeight - 1; yCount >= 0; yCount--) {
				for (xCount = 0; xCount < imageWidth; xCount++) {
					Complex mathsPoint = imagePointToComplex(xCount, yCount);
					image.setRGB(xCount, yCount, point(mathsPoint, iterations).getRGB());
				}
			}
			return image;
		}
		return null;
	}

	private Color point(Complex c, int iterations) {
		Complex z = new Complex(c.getReal(), c.getImaginary());
		int count = 0;
		while (z.modulusSquared() < Integer.MAX_VALUE && count < iterations) {
			z = z.square().add(c);
			count++;
		}
		return colourPoint(z, count, iterations);
	}

}