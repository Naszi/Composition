package hu.naszi.example.composition;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

	private final int[] rules = {

	AlphaComposite.DST, AlphaComposite.DST_ATOP, AlphaComposite.DST_OUT,
			AlphaComposite.SRC, AlphaComposite.SRC_ATOP, AlphaComposite.SRC_OUT };

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		for (int x = 20, y = 20, i = 0; i < rules.length; x += 60, i++) {

			AlphaComposite alphaComposite = AlphaComposite.getInstance(
					rules[i], 0.8f);

			BufferedImage bufferedImage = new BufferedImage(60, 60,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D gbi = bufferedImage.createGraphics();

			gbi.setPaint(Color.blue);
			gbi.fillRect(0, 0, 40, 40);
			gbi.setComposite(alphaComposite);

			gbi.setPaint(Color.green);
			gbi.fillRect(5, 5, 40, 40);

			g2d.drawImage(bufferedImage, x, y, null);
			gbi.dispose();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}
}

public class CompositionEx extends JFrame {

	public CompositionEx() {

		add(new Surface());

		setTitle("Composition");
		setSize(400, 120);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				CompositionEx ex = new CompositionEx();
				ex.setVisible(true);
			}
		});
	}
}
