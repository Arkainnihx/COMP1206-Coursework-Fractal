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
	
	public Complex multiply(double mult) {
		return new Complex(getReal() * mult, getImaginary() * mult);
	}
	
	public Complex mulitply(Complex mult) {
		double mReal, mImaginary;
		mReal = (this.real * mult.real) - (this.imaginary * mult.imaginary);
		mImaginary = (this.real * mult.imaginary) + (mult.real * this.imaginary);
		return new Complex (mReal, mImaginary);
	}
	
	public Complex divide (double div) {
		return new Complex(this.getReal() / div, this.getImaginary() / div);
	}
	
	public Complex divide (Complex div) {
		Complex num = this.mulitply(div.conjugate());
		double den = div.mulitply(div.conjugate()).getReal();
		return num.divide(den);
	}
	
	public Complex pow(int power) {
		if (power == 0) {
			return new Complex(0, 0);
		}
		if (power == 1) {
			return this;
		}
		if (power == 2) {
			return this.square();
		}
		if (power < 0) {
			Complex cPow = pow(-power);
			return new Complex(1, 0).divide(cPow);
		}
		if ((Math.log(power) / Math.log(2)) % 1 == 0) {
			return pow(power / 2).square();
		}
		int closestPowerOfTwo = (int) Math.floor((Math.log(power) / Math.log(2)));
		Complex cPow = this.pow((int) Math.pow(2, closestPowerOfTwo));
		for (int count = (int) Math.pow(2, closestPowerOfTwo); count < power; count++) {
			cPow = cPow.mulitply(this);
		}
		return cPow;
	}
	
	public double modulusSquared() {
		return (real * real) + (imaginary * imaginary);
	}
	
	public double modulus() {
		return Math.sqrt(modulusSquared());
	}
	
	public Complex add(Complex add) {
		return new Complex(real + add.real, imaginary + add.imaginary);
	}
	
	public Complex add(double real) {
		return new Complex (this.real + real, imaginary);
	}
	
	public Complex conjugate() {
		return new Complex(real, -imaginary);
	}
	
}