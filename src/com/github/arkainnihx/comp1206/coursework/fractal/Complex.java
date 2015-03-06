package com.github.arkainnihx.comp1206.coursework.fractal;

public class Complex {

	private double real = 0d, imaginary = 0d;
	
	public Complex() {}

	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getImaginary() {
		return imaginary;
	}

	public void setImaginary(double imaginary) {
		this.imaginary = imaginary;
	}
	
	public Complex square() {
		double sReal, sImaginary;
		sReal = (real * real) - (imaginary * imaginary);
		sImaginary = 2 * real * imaginary;
		return new Complex(sReal, sImaginary);
	}
	
	public double modulusSquared() {
		return (real * real) + (imaginary * imaginary);
	}
	
	public Complex add(Complex d) {
		return new Complex(real + d.real, imaginary + d.imaginary);
	}
	
}