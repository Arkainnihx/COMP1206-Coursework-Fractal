package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

public class ColourSpace {

	private ArrayList<Color> colourList = new ArrayList<Color>();
	private int frequencyModifier;
	
	public ColourSpace(Color... colourList) {
		this(10, colourList);
	}

	public ColourSpace(int frequencyModifier, Color... colourList) {
		this.frequencyModifier = frequencyModifier;
		for (Color color : colourList) {
			this.colourList.add(color);
		}
	}
	
	public void addColour(Color colour, int index) {
		colourList.add(index, colour);
	}
	
	public void editColour(Color colour, int index) {
		colourList.remove(index);
		colourList.add(index, colour);
	}
	
	public ArrayList<Color> getColourList() {
		return colourList;
	}

	public Color getStartColour() {
		return colourList.get(0);
	}

	public ArrayList<Color> getMidColours() {
		ArrayList<Color> midPointList = new ArrayList<Color>();
		for (int count = 1; count < colourList.size() - 1; count++) {
			midPointList.add(colourList.get(count));
		}
		return midPointList;
	}

	public Color getEndColour() {
		return colourList.get(colourList.size() - 1);
	}

	public Color getPoint(int index) {
		return colourList.get(index);
	}

	public void setStartPoint(Color startPoint) {
		colourList.set(0, new Color(startPoint.getRed(), startPoint.getGreen(), startPoint.getBlue()));
	}

	public void setEndPoint(Color endPoint) {
		colourList.set(colourList.size() - 1, new Color(endPoint.getRed(), endPoint.getGreen(), endPoint.getBlue()));
	}

	public int getFrequencyModifier() {
		return frequencyModifier;
	}

	public void setFrequencyModifier(int frequencyModifier) {
		this.frequencyModifier = frequencyModifier;
	}

	public Color smoothColouring(double gradientVal, int iterations) {
		gradientVal /= frequencyModifier;
		gradientVal = Math.abs(gradientVal) % ((colourList.size() - 1) * 2);
		if (gradientVal > colourList.size() - 1) {
			gradientVal = ((colourList.size() - 1) * 2) - gradientVal;
		}
		int startIndex = (int) Math.floor(gradientVal);
		int endIndex = (int) Math.floor(gradientVal + 1);
		return interpolateColour(gradientVal % 1, colourList.get(startIndex), colourList.get(endIndex));
	}

	private Color interpolateColour(double gradientVal, Color startPoint, Color endPoint) {
		long red = startPoint.getRed() + Math.round((endPoint.getRed() - startPoint.getRed()) * gradientVal);
		long green = startPoint.getGreen() + Math.round((endPoint.getGreen() - startPoint.getGreen()) * gradientVal);
		long blue = startPoint.getBlue() + Math.round((endPoint.getBlue() - startPoint.getBlue()) * gradientVal);
		Color colour = new Color((int) red, (int) green, (int) blue);
		return colour;
	}
	
	public void reversePoints() {
		Stack<Color> colourStack = new Stack<Color>();
		for (int count = 0; count < colourList.size(); count++) {
			colourStack.push(colourList.remove(count));
		}
		for (int count = 0; count < colourList.size(); count++) {
			colourList.add(colourStack.pop());
		}
	}
}