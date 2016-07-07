package hu.naszi.example.spotlight;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

	private final int RADIUS = 50;
	private Image penguin;
	private int iw;
	private int ih;
	private int x;
	private int y;
	private boolean mouseIn;

	public Surface() {

		initUI();
	}

	private void initUI() {

		loadImage();

		iw = penguin.getWidth(null);
		ih = penguin.getHeight(null);

		addMouseMotionListener(new MyMouseAdapter());
		addMouseListener(new MyMouseAdapter());
	}

	private void loadImage() {
		penguin = new ImageIcon("penguin.png").getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		int midX = (getWidth() - iw) / 2;
		int midY = (getWidth() - ih) / 2;

		BufferedImage bufferedImage = new BufferedImage(getWidth(),
				getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bigr = bufferedImage.createGraphics();

		if (mouseIn) {
			bigr.setPaint(Color.white);
			bigr.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
			bigr.setComposite(AlphaComposite.SrcAtop);
			bigr.drawImage(penguin, midX, midY, iw, ih, this);
		}

		bigr.setComposite(AlphaComposite.SrcOver.derive(0.1f));
		bigr.drawImage(penguin, midX, midY, iw, ih, this);
		bigr.dispose();

		g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), this);
		g2d.dispose();
	}

	private class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {

			mouseIn = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {

			mouseIn = false;
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {

			x = e.getX();
			y = e.getY();

			repaint();
		}
	}
}

public class SpotlightEx extends JFrame {

	public SpotlightEx() {

		initUI();
	}

	private void initUI() {

		add(new Surface());

		setTitle("Spootlight");
		setSize(350, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				SpotlightEx ex = new SpotlightEx();
				ex.setVisible(true);
			}
		});
	}
}
