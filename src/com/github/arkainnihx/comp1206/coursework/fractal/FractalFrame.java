package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FractalFrame extends JFrame {
	
	Mandelbrot mandelbrot = new Mandelbrot();
	Julia julia = new Julia();
	int iterations = 100;
	Point userSelectedPoint = new Point(0,0);
	JPanel pnlMandelbrot = new JPanel() {
		public void paint (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawImage(getMandelbrot(), 0, 0, null);
		}
	};
	JPanel pnlJulia = new JPanel(){
		public void paint (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawImage(getJulia(), 0, 0, null);
		}
	};

	public static void main(String[] args) {
		new FractalFrame();
	}

	public FractalFrame() {
		
		JPanel pnlTop = new JPanel();
		JPanel pnlSideBar = new JPanel();
		
		pnlMandelbrot.addMouseListener(new userSelectedPointListener());
		
		pnlJulia.setPreferredSize(new Dimension(200, 150));
	
		pnlSideBar.add(pnlJulia);
		
		pnlTop.setLayout(new BorderLayout());
		pnlTop.add(pnlMandelbrot, BorderLayout.CENTER);
		pnlTop.add(pnlSideBar, BorderLayout.WEST);
		
		this.setContentPane(pnlTop);
		this.setTitle("Mandelbrot Set Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	public BufferedImage getMandelbrot() {
		return mandelbrot.generate(pnlMandelbrot.getWidth(), pnlMandelbrot.getHeight(), iterations);
	}

	public BufferedImage getJulia() {
		return julia.generate(pnlJulia.getWidth(), pnlJulia.getHeight(), userSelectedPoint, iterations);
	}
	
	private class userSelectedPointListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			userSelectedPoint = e.getPoint();
			pnlJulia.repaint();
		}
	}
	
}