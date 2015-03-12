package com.github.arkainnihx.comp1206.coursework.fractals;

import com.github.arkainnihx.comp1206.coursework.fractal.Complex;

public abstract class FractalType {
	
	public abstract Complex equation(Complex z, Complex c, int order);
	
}