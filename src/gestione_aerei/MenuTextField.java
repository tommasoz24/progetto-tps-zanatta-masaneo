package gestione_aerei;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serial;

import javax.swing.JTextField;

public class MenuTextField extends JTextField {
	@Serial
	private static final long serialVersionUID = 1L;
	private final String nome;

	public MenuTextField(String nome, final Mondo mondo) {
		setColumns(20);
		setFont(new Font("Arial", Font.PLAIN, 18));
		setSelectedTextColor(Color.BLUE);
		setToolTipText("Inserisci l'indirizzo di " + nome.toLowerCase());
		this.nome = nome;
		setText(nome);

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField f = (JTextField) e.getSource();
				f.selectAll();
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						mondo.menu.bottoneAvvia();
						mondo.requestFocus();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		setFocusable(true);
	}

	public void reset() {
		setText(nome);
	}
}