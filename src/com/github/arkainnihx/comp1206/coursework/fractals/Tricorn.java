package com.github.arkainnihx.comp1206.coursework.fractals;

import com.github.arkainnihx.comp1206.coursework.fractal.Complex;

public class Tricorn extends FractalType {

	public Complex equation(Complex z, Complex c, int order) {
		return z.conjugate().pow(order).add(c);
	}

}
