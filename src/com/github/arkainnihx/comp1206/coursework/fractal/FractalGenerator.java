package com.github.arkainnihx.comp1206.coursework.fractal;

public class FractalGenerator {

	public double MandelbrotPoint(Complex c, int iterations) {
		Complex z = new Complex(c.getReal(), c.getImaginary());
		for (int count = 0; count < iterations; count++) {
			z = z.square().add(c);
		}
		return Math.sqrt(z.modulusSquared());
	}
	
}