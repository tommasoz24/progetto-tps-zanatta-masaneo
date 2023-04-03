package gestione_aerei;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Objects;

public class MappaCentrale extends PannelloMappa {
    @Serial
    private static final long serialVersionUID = 1L;
    private double latCorrente;
    private double logCorrente;
    private int velocita;
    private boolean autoAtterra;

    private final Format formatter;

    // menu
    private final MenuZoom zoomPanel;

    // images
    private final BufferedImage controlImg;
    private final Image gaugesImg;
    private final Image radarImg;
    private final Image gauges1Img;
    private final Image gauges2Img;
    private final Image speedometer;
    private final Image alien;

    // fonts
    private final Font latLogFont;
    private final Font speedFont;
    private final Font alienFont;
    private final Font landFont;

    public MappaCentrale(double latCorrente, double logCorrente) throws IOException {
        width = 600;
        height = 600;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        view = "satellite";

        final JMenuBar menu = new JMenuBar();
        menu.add(new MenuView(this));
        menu.add(Box.createHorizontalStrut(304));
        zoomPanel = new MenuZoom(this, 5);
        menu.add(zoomPanel);
        add(menu, BorderLayout.NORTH);
        autoAtterra = false;

        formatter = new DecimalFormat("#0.###");

        this.latCorrente = latCorrente;
        this.logCorrente = logCorrente;

        img = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/centerMap.png")));

        controlImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/controlslong.png")));
        radarImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/radar.gif"))).getImage();
        gaugesImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/gauges.gif"))).getImage();
        gauges1Img = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/gauges1.gif"))).getImage();
        gauges2Img = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/gauges2.gif"))).getImage();
        speedometer = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/speedometer.gif"))).getImage();
        alien = new ImageIcon(Objects.requireNonNull(getClass().getResource("immagini/alien.gif"))).getImage();

        latLogFont = new Font("Arial", Font.PLAIN, 13);
        speedFont = latLogFont.deriveFont(24f);
        alienFont = latLogFont.deriveFont(20f);
        landFont = new Font("Arial", Font.BOLD, 40);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, 442, 485, null);
        g.drawImage(controlImg, 0, 22, 472, 520, null);
        g.setColor(Color.RED);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(radarImg, 253, 37, 25, 25, null);
        g2.drawImage(gaugesImg, 156, 484, 41, 35, null);
        g2.drawImage(gauges1Img, 221, 484, 43, 36, null);
        g2.drawImage(gauges2Img, 280, 484, 41, 35, null);
        g2.drawImage(speedometer, 350, 23, 70, 30, null);

        g.setFont(speedFont);
        g.drawString(String.valueOf(velocita * 10), 370, 63);
        drawLatLog(g);

        if (autoAtterra) {
            g.setFont(landFont);
            g.drawString("Modalita' AutoAtterraggio", 70, 300);
        }

        if (latCorrente < -90 || latCorrente > 90) {
            for (int i = 55; i <= 355; i += 50) {
                g2.drawImage(alien, i, height / 2, 63, 101, null);
            }
            g2.setColor(Color.GREEN);
            g2.setFont(alienFont);
            g2.drawString("ZAP! Fuori range.", 150, 205);
            g2.drawString("RITORNA NELLA MAPPA!!", 60, 240);
        }
    }

    private void drawLatLog(Graphics g) {
        char latsym = 'N';
        if (latCorrente < 0) {
            latsym = 'S';
        }
        char logsym = 'E';
        if (logCorrente < 0) {
            logsym = 'W';
        }
        g.setFont(latLogFont);
        g.drawString(formatter.format(Math.abs(latCorrente)) + "°" + latsym + ", "
                + formatter.format(Math.abs(logCorrente)) + "°" + logsym, 10, 50);
    }

    public void loadImg() throws MalformedURLException {
        URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + latCorrente + "," + logCorrente
                + "&size=640x640" + "&maptype=" + view + "&zoom=" + zoomPanel.zoom
                + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
        new ImgDownloadThread(url, this).start();
    }

    public void updateView(String view) throws MalformedURLException {
        this.view = view.toLowerCase();
        loadImg();
    }

    public void updateMap(int velocita, double latCorrente, double logCorrente,
						  boolean autoAtterra) throws MalformedURLException {
        this.autoAtterra = autoAtterra;
        this.latCorrente = latCorrente;
        this.logCorrente = logCorrente;
        this.velocita = velocita;
        loadImg();
    }
}
