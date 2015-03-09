package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Julia extends Fractal {
	
	Complex c = new Complex(0,0);

	public BufferedImage generate(int imageWidth, int imageHeight, Complex userSelectedPoint, int iterations) {
		widthConstant = pixelConstant(imageWidth, realUB, realLB);
		heightConstant = pixelConstant(imageHeight, imaginaryUB, imaginaryLB);
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
	
	private Color point(Complex d, int iterations) {
		Complex z = new Complex(d.getReal(), d.getImaginary());
		int count = 0;
		while (z.modulusSquared() < 4 && count < iterations) {
			z = z.square().add(c);
			count++;
		}
		return colourPoint(z, count, iterations);
	}

}