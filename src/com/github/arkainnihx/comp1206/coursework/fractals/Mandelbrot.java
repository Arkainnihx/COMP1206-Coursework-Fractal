package com.github.arkainnihx.comp1206.coursework.fractals;

import com.github.arkainnihx.comp1206.coursework.fractal.Complex;

public class Mandelbrot extends FractalType {

	public Complex equation(Complex z, Complex c, int order) {
		return z.pow(order).add(c);
	}

}
