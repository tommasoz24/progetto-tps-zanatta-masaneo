package meteoAnimato;

import java.awt.Color;
import java.io.Serial;

import javax.swing.JFrame;

public class Neve extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	public Neve() {
		setSize(200, 200);
		setBackground(Color.GRAY);
		add(new NeveComponent());
	}

	public static void main(String[] args) {
		Neve neve = new Neve();
		neve.setVisible(true);

		Thread t = new Thread(() -> {
			while (true) {

				neve.repaint();
				try {
					Thread.sleep(500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t.start();
	}
}