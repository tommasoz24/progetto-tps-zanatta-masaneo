package gestione_aerei;

import java.io.IOException;

import javax.swing.JDialog;

public class InstructionDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public InstructionDialog() throws IOException {
		setSize(700, 600);
		setLocationRelativeTo(null);
		setTitle("Istruzioni di gioco");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		add(new InstructionsComponent());
		setVisible(true);
	}
}