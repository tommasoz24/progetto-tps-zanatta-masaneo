package gestione_aerei;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.net.MalformedURLException;

public class MenuZoom extends JMenuItem {
    @Serial
    private static final long serialVersionUID = 1L;

    protected int zoom;

    private final JButton zoomout;
    private JButton zoomin;
    private final PannelloMappa parentPanel;

    public MenuZoom(PannelloMappa parentPanel, int initialZoom) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.parentPanel = parentPanel;

        zoom = initialZoom;
        zoomout = new JButton("-");
        zoomout.setToolTipText("zoomma meno");
        ActionListener zoomoutListen = event -> {
            zoom--;
            if (zoom == 1) {
                zoomout.setEnabled(false);
            }
            if (!zoomin.isEnabled()) {
                zoomin.setEnabled(true);
            }
            try {
                load();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        };
        zoomout.addActionListener(zoomoutListen);
        add(zoomout);

        zoomin = new JButton("+");
        zoomin.setToolTipText("zoomma di piu");
        ActionListener zoominListen = event -> {
            zoom++;
            if (zoom == 21) {
                zoomin.setEnabled(false);
            }
            if (!zoomout.isEnabled()) {
                zoomout.setEnabled(true);
            }
            try {
                load();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        };
        zoomin.addActionListener(zoominListen);
        add(zoomin);
    }

    private void load() throws MalformedURLException {
        if (parentPanel instanceof MappaNavigazione) {
            ((MappaNavigazione) parentPanel).caricaImg();
        } else if (parentPanel instanceof MappaCentrale) {
            ((MappaCentrale) parentPanel).loadImg();
        }
    }
}