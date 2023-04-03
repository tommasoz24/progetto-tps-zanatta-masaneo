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
	private int gradi;
	private final BufferedImage aereoImg;

	public Aereo(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		aereoImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/airplane.jpg")));
	}

	public void setGradi(int direzione) {
		switch (direzione) {
			case 2 -> gradi = 135;
			case 4 -> gradi = -135;
			case 6 -> gradi = 45;
			case 8 -> gradi = -45;
		}
	}

	public void paintComponent(Graphics g) {
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(gradi), (double) aereoImg.getWidth() / 2,
				(double) aereoImg.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(aereoImg, null), x, y, 20, 20, null);
	}

	public void reset() {
		x = 150;
		y = 272 / 2 - 10;
	}

	public void setY(int difference) {
		y += difference;
	}

	public void setX(int difference) {
		x += difference;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}