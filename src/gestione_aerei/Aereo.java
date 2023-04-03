package gestione_aerei;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Aereo {
	private int x;
	private int y;
	private int degrees;
	private final BufferedImage planeImg;

	public Aereo(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		planeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/airplane.jpg")));
	}

	public void setDegree(int direction) {
		switch (direction) {
			case 2 -> degrees = 135;
			case 4 -> degrees = -135;
			case 6 -> degrees = 45;
			case 8 -> degrees = -45;
		}
	}

	public void paintComponent(Graphics g) {
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degrees), (double) planeImg.getWidth() / 2,
				(double) planeImg.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(planeImg, null), x, y, 20, 20, null);
	}

	public void reset() {
		x = 150;
		y = 272 / 2 - 10;
	}

	public void changeY(int difference) {
		y += difference;
	}

	public void changeX(int difference) {
		x += difference;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}