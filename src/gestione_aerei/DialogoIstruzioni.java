package gestione_aerei;

import java.io.IOException;
import java.io.Serial;

import javax.swing.JDialog;

public class DialogoIstruzioni extends JDialog {
	@Serial
	private static final long serialVersionUID = 1L;

	public DialogoIstruzioni() throws IOException {
		setSize(700, 600);
		setLocationRelativeTo(null);
		setTitle("Istruzioni di gioco");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		add(new ComponentIstruzioni());
		setVisible(true);
	}
}