package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
	int iterations = 50;
	Complex userSelectedPoint = new Complex(0, 0);
	FractalPanel pnlMainFractal = new FractalPanel(mandelbrot);
	FractalPanel pnlSideFractal = new FractalPanel(julia);

	public static void main(String[] args) {
		new FractalFrame();
	}

	public FractalFrame() {

		JPanel pnlTop = new JPanel();
		JPanel pnlSideBar = new JPanel();
		JPanel pnlSideBarNorth = new JPanel();
		JPanel pnlComplexPoint = new JPanel();
		JLabel lblComplexPoint = new JLabel("Selected c = ");
		final JTextField txtRealUB = new JTextField(10);
		final JTextField txtRealLB = new JTextField(10);
		final JTextField txtImaginaryUB = new JTextField(10);
		final JTextField txtImaginaryLB = new JTextField(10);
		final JTextField txtPointX = new JTextField(5);
		final JTextField txtPointY = new JTextField(5);
		ActionListener setMathboundsListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlMainFractal.fractal.setBounds(Double.parseDouble(txtRealUB.getText()),
						Double.parseDouble(txtRealLB.getText()), Double.parseDouble(txtImaginaryUB.getText()),
						Double.parseDouble(txtImaginaryLB.getText()));
			}
		};
		MouseAdapter userSelectedPointListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				userSelectedPoint = mandelbrot.imagePointToComplex(e.getX(), e.getY());
				julia.setC(userSelectedPoint);
				txtPointX.setText(String.valueOf(roundDouble(userSelectedPoint.getReal(), 4)));
				txtPointY.setText(String.valueOf(roundDouble(userSelectedPoint.getImaginary(), 4)));
				pnlSideFractal.repaint();
			}

			public void mouseMoved(MouseEvent e) {
				userSelectedPoint = mandelbrot.imagePointToComplex(e.getX(), e.getY());
				julia.setC(userSelectedPoint);
				txtPointX.setText(String.valueOf(roundDouble(userSelectedPoint.getReal(), 4)));
				txtPointY.setText(String.valueOf(roundDouble(userSelectedPoint.getImaginary(), 4)));
				pnlSideFractal.repaint();
			}
		};
		
		txtRealUB.addActionListener(setMathboundsListener);
		txtRealLB.addActionListener(setMathboundsListener);
		txtImaginaryUB.addActionListener(setMathboundsListener);
		txtImaginaryLB.addActionListener(setMathboundsListener);

		pnlMainFractal.addMouseListener(userSelectedPointListener);
		pnlMainFractal.addMouseMotionListener(userSelectedPointListener);

		pnlSideFractal.setPreferredSize(new Dimension(200, 150));

		pnlComplexPoint.add(lblComplexPoint);
		pnlComplexPoint.add(txtPointX);
		pnlComplexPoint.add(txtPointY);

		pnlSideBarNorth.add(pnlSideFractal);

		pnlSideBar.setLayout(new BorderLayout());
		pnlSideBar.add(pnlSideBarNorth, BorderLayout.NORTH);
		
		pnlSideBar.add(pnlComplexPoint, BorderLayout.CENTER);

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

	private double roundDouble(double value, int pow) {
		return Math.round(value * Math.pow(10, pow)) / Math.pow(10, pow);
	}

	public class FractalPanel extends JPanel {
		private Fractal fractal;

		public FractalPanel(Fractal fractal) {
			this.fractal = fractal;
			this.addComponentListener(new ComponentListener() {
				public void componentResized(ComponentEvent e) {
					FractalPanel.this.fractal.setImageConstraints(FractalPanel.this.getWidth(), FractalPanel.this.getHeight());
					repaint();
				}
				public void componentShown(ComponentEvent e) {}
				public void componentMoved(ComponentEvent e) {}
				public void componentHidden(ComponentEvent e) {}
			});
		}

		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			BufferedImage fractalImage = fractal.generate(iterations);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawImage(fractalImage, 0, 0, null);
		}
	}

}