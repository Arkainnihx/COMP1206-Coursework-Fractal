package com.github.arkainnihx.comp1206.coursework.fractal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.github.arkainnihx.comp1206.coursework.fractals.FractalType;

public class FractalState implements Serializable {
	
	private static final long serialVersionUID = 4953406325214274877L;
	private FractalType type;
	private boolean isJulia;
	private double realUB, realLB, imaginaryUB, imaginaryLB;
	private int order;
	private ColourSpace gradient;
	private boolean isBlackSet;
	private Complex juliaAnchor;
	
	public FractalState(String name, Fractal fractal) {
		this.type = fractal.getType();
		this.isJulia = fractal.isJulia();
		this.realUB = fractal.getRealUB();
		this.realLB = fractal.getRealLB();
		this.imaginaryUB = fractal.getImaginaryUB();
		this.imaginaryLB = fractal.getImaginaryLB();
		this.order = fractal.getOrder();
		this.gradient = fractal.getGradient();
		this.isBlackSet = fractal.isBlackSet();
		this.juliaAnchor = fractal.getJuliaAnchor();
	}
	
	public static boolean writeToFile(FractalState savedFavourite) {
		File favourites = new File("favourites.ser");
		FileOutputStream fileWriter;
		ObjectOutputStream objectWriter;
		try {
			if (favourites.exists()) {
				fileWriter = new FileOutputStream(favourites, true);
			} else {
				fileWriter = new FileOutputStream(favourites);
			}
			objectWriter = new ObjectOutputStream(fileWriter);
			objectWriter.writeObject(savedFavourite);
			objectWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}	
	}
	
	public static ArrayList<FractalState> readFileEntries() {
		
	}
	
	public Fractal convertToFractal() {
		return new Fractal(this.type, this.isJulia, this.realUB, this.realLB, this.imaginaryUB, this.imaginaryLB, 0, 0, 0, 0,
				this.order, this.gradient, this.isBlackSet, this.juliaAnchor);
	}

}