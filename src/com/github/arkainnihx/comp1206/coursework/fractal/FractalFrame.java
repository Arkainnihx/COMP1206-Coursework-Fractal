package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FractalFrame extends JFrame {
	
	FractalGenerator gen = new FractalGenerator();
	
	public static void main(String[] args) {
		new FractalFrame();
	}

	public FractalFrame() {
		
		JPanel pnlTop = new JPanel();
		JPanel pnlMandelbrot = new JPanel();
		JPanel pnlSideBar = new JPanel();
		JPanel pnlJulia = new JPanel();
		
		
		pnlTop.setLayout(new BorderLayout());
		
		this.setContentPane(pnlTop);
		this.setTitle("Mandelbrot Set Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
}