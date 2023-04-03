package meteoAnimato;

import java.awt.Color;
import java.io.Serial;
import javax.swing.JFrame;

public class Pioggia extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	public Pioggia() {
		setSize(200, 200);
		setBackground(Color.GRAY);
		add(new PioggiaComponent());
	}

	public static void main(String[] args) {
		Pioggia pioggia = new Pioggia();
		pioggia.setVisible(true);

		Thread t = new Thread(() -> {
			while (true) {
				pioggia.repaint();
				try {
					Thread.sleep(10);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
	}
}