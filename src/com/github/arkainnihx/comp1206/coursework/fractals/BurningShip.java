package com.github.arkainnihx.comp1206.coursework.fractals;

import com.github.arkainnihx.comp1206.coursework.fractal.Complex;

public class BurningShip extends FractalType {

	public Complex equation(Complex z, Complex c, int order) {
		return new Complex(Math.abs(z.getReal()), Math.abs(z.getImaginary())).pow(order).conjugate().add(c);
	}

}
