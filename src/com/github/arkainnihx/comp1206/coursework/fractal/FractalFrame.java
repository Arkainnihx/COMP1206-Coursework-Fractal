package com.github.arkainnihx.comp1206.coursework.fractal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class FractalFrame extends JFrame {

	int iterations = 100;
	Complex userSelectedPoint = new Complex(0, 0);
	FractalPanel pnlMainFractal = new FractalPanel(new Fractal());
	FractalPanel pnlSideFractal = new FractalPanel(new Fractal(true));
	boolean isDrag = false;
	int xDrag, yDrag, widthDrag, heightDrag;

	public static void main(String[] args) {
		new FractalFrame();
	}

	public FractalFrame() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		JPanel pnlTop = new JPanel();
		JPanel pnlSideBar = new JPanel();
		JPanel pnlSideBarNorth = new JPanel();
		JPanel pnlSideBarCenter = new JPanel();
		JPanel pnlMathsBounds = new JPanel();
		JPanel pnlComplexPoint = new JPanel();
		final JTextField txtIterations = new JTextField(5);
		final JTextField txtRealUB = new JTextField(8);
		final JTextField txtRealLB = new JTextField(8);
		final JTextField txtImaginaryUB = new JTextField(8);
		final JTextField txtImaginaryLB = new JTextField(8);
		final JTextField txtPointReal = new JTextField(5);
		final JTextField txtPointImaginary = new JTextField(5);
		ActionListener setMathboundsListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlMainFractal.fractal.setBounds(Double.parseDouble(txtRealUB.getText()),
						Double.parseDouble(txtRealLB.getText()), Double.parseDouble(txtImaginaryUB.getText()),
						Double.parseDouble(txtImaginaryLB.getText()));
				pnlMainFractal.repaint();
			}
		};
		MouseAdapter mainPanelMouseListener = new MouseAdapter() {
			private Point prevMouseCoords = new Point();
			private Stack<double[]> zoomStack = new Stack<double[]>();

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					userSelectedPoint = pnlMainFractal.fractal.imagePointToComplex(e.getX(), e.getY());
					pnlSideFractal.fractal.setJuliaAnchor(userSelectedPoint);
					txtPointReal.setText(String.valueOf(roundDouble(userSelectedPoint.getReal(), 5)));
					txtPointImaginary.setText(String.valueOf(roundDouble(userSelectedPoint.getImaginary(), 5)));
					pnlSideFractal.repaint();
				} else {
					
				}
			}
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					prevMouseCoords = e.getPoint();
				}
			}
			public void mouseDragged(MouseEvent e) {
					isDrag = true;
					xDrag = (int) Math.min(prevMouseCoords.getX(), e.getPoint().getX());
					yDrag = (int) Math.min(prevMouseCoords.getY(), e.getPoint().getY());
					widthDrag = (int) Math.max(prevMouseCoords.getX(), e.getPoint().getX()) - xDrag;
					heightDrag = (int) Math.max(prevMouseCoords.getY(), e.getPoint().getY()) - yDrag;
					pnlMainFractal.repaint();
			}
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && isDrag) {
					isDrag = false;
					int xMin = (int) Math.min(prevMouseCoords.getX(), e.getPoint().getX());
					int xMax = (int) Math.max(prevMouseCoords.getX(), e.getPoint().getX());
					int yMin = (int) Math.min(prevMouseCoords.getY(), e.getPoint().getY());
					int yMax = (int) Math.max(prevMouseCoords.getY(), e.getPoint().getY());
					Point topRight = new Point(xMax, yMin);
					Point bottomLeft = new Point(xMin, yMax);
					Complex upperBounds = pnlMainFractal.fractal.imagePointToComplex(topRight.x, topRight.y);
					Complex lowerBounds = pnlMainFractal.fractal.imagePointToComplex(bottomLeft.x, bottomLeft.y);
					txtRealUB.setText(String.valueOf(roundDouble(upperBounds.getReal(), 9)));
					txtRealLB.setText(String.valueOf(roundDouble(lowerBounds.getReal(), 9)));
					txtImaginaryUB.setText(String.valueOf(roundDouble(upperBounds.getImaginary(), 9)));
					txtImaginaryLB.setText(String.valueOf(roundDouble(lowerBounds.getImaginary(), 9)));
					pnlMainFractal.fractal.setBounds(upperBounds.getReal(), lowerBounds.getReal(), upperBounds.getImaginary(),
							lowerBounds.getImaginary());
					pnlMainFractal.repaint();
				}
			}
			public void mouseMoved(MouseEvent e) {
				userSelectedPoint = pnlMainFractal.fractal.imagePointToComplex(e.getX(), e.getY());
				pnlSideFractal.fractal.setJuliaAnchor(userSelectedPoint);
				txtPointReal.setText(String.valueOf(roundDouble(userSelectedPoint.modulusSquared(), 5)));
				txtPointImaginary.setText(String.valueOf(roundDouble(userSelectedPoint.getImaginary(), 5)));
				pnlSideFractal.repaint();
			}
		};

		txtIterations.setHorizontalAlignment(JTextField.RIGHT);
		txtIterations.setText(String.valueOf(iterations));
		txtIterations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iterations = Integer.parseInt(txtIterations.getText());
				pnlMainFractal.repaint();
				pnlSideFractal.repaint();
			}
		});

		txtRealUB.setHorizontalAlignment(JTextField.RIGHT);
		txtRealUB.setText(String.valueOf(pnlMainFractal.fractal.getRealUB()));
		txtRealUB.addActionListener(setMathboundsListener);

		txtRealLB.setHorizontalAlignment(JTextField.RIGHT);
		txtRealLB.setText(String.valueOf(pnlMainFractal.fractal.getRealLB()));
		txtRealLB.addActionListener(setMathboundsListener);

		txtImaginaryUB.setHorizontalAlignment(JTextField.RIGHT);
		txtImaginaryUB.setText(String.valueOf(pnlMainFractal.fractal.getImaginaryUB()));
		txtImaginaryUB.addActionListener(setMathboundsListener);

		txtImaginaryLB.setHorizontalAlignment(JTextField.RIGHT);
		txtImaginaryLB.setText(String.valueOf(pnlMainFractal.fractal.getImaginaryLB()));
		txtImaginaryLB.addActionListener(setMathboundsListener);

		txtPointReal.setHorizontalAlignment(JTextField.RIGHT);
		txtPointReal.setText(String.valueOf(userSelectedPoint.getReal()));

		txtPointImaginary.setHorizontalAlignment(JTextField.RIGHT);
		txtPointImaginary.setText(String.valueOf(userSelectedPoint.getImaginary()));

		pnlMainFractal.addMouseListener(mainPanelMouseListener);
		pnlMainFractal.addMouseMotionListener(mainPanelMouseListener);

		pnlSideFractal.setPreferredSize(new Dimension(250, 200));

		pnlMathsBounds.setLayout(new GridLayout(5, 2));
		pnlMathsBounds.setPreferredSize(new Dimension(pnlSideFractal.getPreferredSize().width, 150));
		pnlMathsBounds.add(new JLabel("Iterations:"));
		pnlMathsBounds.add(new JPanel() { { this.add(txtIterations); } });
		pnlMathsBounds.add(new JLabel("Real UB:"));
		pnlMathsBounds.add(new JPanel() { { this.add(txtRealUB); } });
		pnlMathsBounds.add(new JLabel("Real LB:"));
		pnlMathsBounds.add(new JPanel() { { this.add(txtRealLB); } });
		pnlMathsBounds.add(new JLabel("Imaginary UB:"));
		pnlMathsBounds.add(new JPanel() { { this.add(txtImaginaryUB); }	});
		pnlMathsBounds.add(new JLabel("Imaginary LB:"));
		pnlMathsBounds.add(new JPanel() { { this.add(txtImaginaryLB); } });

		pnlComplexPoint.add(new JLabel("Selected c ="));
		pnlComplexPoint.add(txtPointReal);
		pnlComplexPoint.add(txtPointImaginary);

		pnlSideBarNorth.add(pnlSideFractal);

		pnlSideBarCenter.setPreferredSize(pnlSideFractal.getPreferredSize());
		pnlSideBarCenter.add(pnlMathsBounds);
		pnlSideBarCenter.add(pnlComplexPoint);

		pnlSideBar.setLayout(new BorderLayout());
		pnlSideBar.add(pnlSideBarNorth, BorderLayout.NORTH);
		pnlSideBar.add(pnlSideBarCenter, BorderLayout.CENTER);

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
	
	private void swapFractals() {
		
	}

	private double roundDouble(double value, int pow) {
		return Math.round(value * Math.pow(10, pow)) / Math.pow(10, pow);
	}

	public class FractalPanel extends JPanel {
		private Fractal fractal;
		private BufferedImage fractalImage;

		public FractalPanel(Fractal fractal) {
			this.fractal = fractal;
			this.addComponentListener(new ComponentListener() {
				public void componentResized(ComponentEvent e) {
					FractalPanel.this.fractal.setImageSize(FractalPanel.this.getWidth(), FractalPanel.this.getHeight());
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
			if (!isDrag) {
				fractalImage = fractal.generate(iterations);
			}
			g2.drawImage(fractalImage, 0, 0, null);
			if (isDrag) {
				g2.setXORMode(getBackground());
				g2.drawRect(xDrag, yDrag, widthDrag, heightDrag);
			}
		}
	}

}