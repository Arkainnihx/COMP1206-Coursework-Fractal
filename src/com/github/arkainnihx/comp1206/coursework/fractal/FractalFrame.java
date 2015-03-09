package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FractalFrame extends JFrame {
	
	Mandelbrot mandelbrot = new Mandelbrot();
	Julia julia = new Julia();
	int iterations = 100;
	Complex userSelectedPoint = new Complex(0,0);
	FractalPanel pnlMainFractal = new FractalPanel();
	FractalPanel pnlSideFractal = new FractalPanel();

	public static void main(String[] args) {
		new FractalFrame();
	}

	public FractalFrame() {
		
		JPanel pnlTop = new JPanel();
		JPanel pnlSideBar = new JPanel();
		JPanel pnlSideBarNorth = new JPanel();
		JPanel pnlComplexPoint = new JPanel();
		JLabel lblComplexPoint = new JLabel("Selected c = ");
		JTextField txtRealUB = new JTextField(10);
		JTextField txtRealLB = new JTextField(10);
		JTextField txtImaginaryUB = new JTextField(10);
		JTextField txtImaginaryLB = new JTextField(10);
		final JTextField txtPointX = new JTextField(5);
		final JTextField txtPointY = new JTextField(5);
		ActionListener setMathboundsListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlMainFractal.fractal.setBounds(txtRealUB.getText(), realLB, imaginaryUB, imaginaryLB);
			}
		};
		MouseAdapter userSelectedPointListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				userSelectedPoint = mandelbrot.imagePointToComplex(e.getX(), e.getY());
				txtPointX.setText(String.valueOf(roundDouble(userSelectedPoint.getReal(), 4)));
				txtPointY.setText(String.valueOf(roundDouble(userSelectedPoint.getImaginary(), 4)));
				pnlSideFractal.repaint();
			}
			
//			public void mouseMoved(MouseEvent e) {
//				userSelectedPoint = mandelbrot.imagePointToComplex(e.getX(), e.getY());
//				pnlSideFractal.repaint();
//			}
		};

		pnlMainFractal.addMouseListener(userSelectedPointListener);
		pnlMainFractal.addMouseMotionListener(userSelectedPointListener);
		
		pnlSideFractal.setPreferredSize(new Dimension(200, 150));
	
		pnlComplexPoint.add(lblComplexPoint);
		pnlComplexPoint.add(txtPointX);
		pnlComplexPoint.add(txtPointY);
		
		pnlSideBarNorth.add(pnlSideFractal);
		
		pnlSideBar.setLayout(new BorderLayout());
		pnlSideBar.add(pnlSideBarNorth, BorderLayout.NORTH);
		pnlSideBar.add(pnlComplexPoint);
		
		pnlTop.setLayout(new BorderLayout());
		pnlTop.add(pnlMainFractal, BorderLayout.CENTER);
		pnlTop.add(pnlSideBar, BorderLayout.WEST);
		
		this.setContentPane(pnlTop);
		this.setTitle("Mandelbrot Set Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
		
	}
	
	public BufferedImage getMandelbrot() {
		return mandelbrot.generate(pnlMainFractal.getWidth(), pnlMainFractal.getHeight(), iterations);
	}

	public BufferedImage getJulia() {
		return julia.generate(pnlSideFractal.getWidth(), pnlSideFractal.getHeight(), userSelectedPoint, iterations);
	}
	
	private double roundDouble(double value, int pow) {
		return Math.round(value * Math.pow(10, pow)) / Math.pow(10, pow);
	}
	
	public class FractalPanel extends JPanel {
		private Fractal fractal;
		public void paint (Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawImage(getMandelbrot(), 0, 0, null);
		}
	}

}