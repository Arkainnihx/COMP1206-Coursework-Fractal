package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

public class ColourSpace implements Cloneable {

	private ArrayList<Color> pointList = new ArrayList<Color>();

	public ColourSpace(Color... colourList) {
		for (Color color : colourList) {
			pointList.add(color);
		}
	}

	public Color getStartPoint() {
		return pointList.get(0);
	}

	public ArrayList<Color> getMidPoints() {
		ArrayList<Color> midPointList = new ArrayList<Color>();
		for (int count = 1; count < pointList.size() - 1; count++) {
			midPointList.add(pointList.get(count));
		}
		return midPointList;
	}

	public Color getEndPoint() {
		return pointList.get(pointList.size() - 1);
	}

	public Color getPoint(int index) {
		return pointList.get(index);
	}

	public void setStartPoint(Color startPoint) {
		pointList.set(0, new Color(startPoint.getRed(), startPoint.getGreen(), startPoint.getBlue()));
	}

	public void setEndPoint(Color endPoint) {
		pointList.set(pointList.size() - 1, new Color(endPoint.getRed(), endPoint.getGreen(), endPoint.getBlue()));
	}

	public Color generateColour(float gradientVal) {
		if (pointList.size() > 2) {
			int count = 0;
			double segmentCount = 0d;
			double segmentLength = 1d;
			segmentLength /= (pointList.size() - 1);
			while (segmentCount < gradientVal) {
				segmentCount += segmentLength;
				count++;
			}
			gradientVal = (float) (gradientVal + segmentLength - segmentCount) * (pointList.size() - 1);
			return generateColour(gradientVal, pointList.get(count - 1), pointList.get(count));
		} else {
			return generateColour(gradientVal, getStartPoint(), getEndPoint());
		}
	}

	private Color generateColour(float gradientVal, Color startPoint, Color endPoint) {
		int red = startPoint.getRed() + Math.round((endPoint.getRed() - startPoint.getRed()) * gradientVal);
		int green = startPoint.getGreen() + Math.round((endPoint.getGreen() - startPoint.getGreen()) * gradientVal);
		int blue = startPoint.getBlue() + Math.round((endPoint.getBlue() - startPoint.getBlue()) * gradientVal);
		return new Color(red, green, blue);
	}

	public void reversePoints() {
		Stack<Color> colourStack = new Stack<Color>();
		for (int count = 0; count < pointList.size(); count++) {
			colourStack.push(pointList.remove(count));
		}
		for (int count = 0; count < pointList.size(); count++) {
			pointList.add(colourStack.pop());
		}
	}
}