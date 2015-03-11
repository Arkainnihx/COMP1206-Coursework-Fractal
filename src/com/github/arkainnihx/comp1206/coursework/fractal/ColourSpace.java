package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

public class ColourSpace implements Cloneable {

	private ArrayList<Color> colourList = new ArrayList<Color>();
	private ArrayList<Color> palette = new ArrayList<Color>();

	public ColourSpace(Color... colourList) {
		for (Color color : colourList) {
			this.colourList.add(color);
		}
	}

	public Color getStartPoint() {
		return colourList.get(0);
	}

	public ArrayList<Color> getMidPoints() {
		ArrayList<Color> midPointList = new ArrayList<Color>();
		for (int count = 1; count < colourList.size() - 1; count++) {
			midPointList.add(colourList.get(count));
		}
		return midPointList;
	}

	public Color getEndPoint() {
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

	public Color smoothColouring(double gradientVal, int iterations) {
		gradientVal %= (colourList.size() - 1) * 2;
		if (gradientVal > colourList.size() - 1) {
			gradientVal = ((colourList.size() - 1) * 2) - gradientVal;
		}
		int startIndex = (int) Math.floor(gradientVal);
		int endIndex = (int) Math.floor(gradientVal + 1);
		return interpolateColour(gradientVal % 1, colourList.get(startIndex), colourList.get(endIndex));
//		if (gradientVal > 1)
//			gradientVal = 2 - gradientVal;
//		if (pointList.size() > 2) {
//			int count = 0;
//			double segmentCount = 0d;
//			double segmentLength = 1d;
//			segmentLength /= (pointList.size() - 1);
//			while (segmentCount < gradientVal) {
//				segmentCount += segmentLength;
//				count++;
//			}
//			gradientVal = (float) (gradientVal + segmentLength - segmentCount) * (pointList.size() - 1);
//			return generateColour(gradientVal, pointList.get(count - 1), pointList.get(count));
//		} else {
//			return generateColour(gradientVal, getStartPoint(), getEndPoint());
//		}
	}

	private Color interpolateColour(double gradientVal, Color startPoint, Color endPoint) {
		long red = startPoint.getRed() + Math.round((endPoint.getRed() - startPoint.getRed()) * gradientVal);
		long green = startPoint.getGreen() + Math.round((endPoint.getGreen() - startPoint.getGreen()) * gradientVal);
		long blue = startPoint.getBlue() + Math.round((endPoint.getBlue() - startPoint.getBlue()) * gradientVal);
		Color colour = new Color((int) red, (int) green, (int) blue);
		return colour;
	}
	
	private Color test (double gradientVal) {
		int red = 
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