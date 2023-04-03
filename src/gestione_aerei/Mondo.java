package gestione_aerei;

import meteo.MeteoCont;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class Mondo extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int UP = 8;
    private static final int DOWN = 2;
    private static final int RIGHT = 6;
    private static final int LEFT = 4;
    private static final int INCREASE = 3;
    private static final int DECREASE = -3;

    protected MenuBarMondo menu;
    private final DialogoIstruzioni inst;

    private final MappaLaterale mappaLaterale;
    private final MeteoCont weather;
    private final MappaCentrale mappaCentrale;

    private double latCorrente;
    private double logCorrente;
    private double latDestinazione;
    private double logDestinazione;

    // plane controls
    private int direzione;
    private int velocita;

    private boolean suono;
    private boolean pausa;
    private boolean atterraggio;
    private boolean autoAtterraggio;

    private Audio cockpit;
    private Audio planeNoise;
    private Audio landingNoise;
    private Audio landed;
    private CountDownLatch latch;

    public Mondo() throws IOException, InterruptedException {
        inst = new DialogoIstruzioni();

        setLayout(new BorderLayout());
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Progetto TPS Zanatta-Masaneo");

        latCorrente = 40.633785;
        logCorrente = -73.779277;

        setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/airplane.jpg"))));
        setFocusable(true);

        menu = new MenuBarMondo(this);

        add(menu, BorderLayout.SOUTH);


        direzione = LEFT;
        velocita = 0;


        autoAtterraggio = false;
        suono = true;
        atterraggio = false;
        pausa = true;

        mappaCentrale = new MappaCentrale(latCorrente, logCorrente);
        add(mappaCentrale, BorderLayout.CENTER);
        mappaLaterale = new MappaLaterale(latCorrente, logCorrente, direzione);
        add(mappaLaterale, BorderLayout.WEST);
        weather = new MeteoCont(latCorrente, logCorrente);
        add(weather, BorderLayout.EAST);
        setupTastiera();

        setVisible(true);

        latch = new CountDownLatch(1);

        cockpit = new Audio(latch, 10000, "suono/seat.wav", false);
        cockpit.start();
        latch.await();
        if (suono) {
            planeNoise = new Audio(latch, 0, "suono/airTraffic.wav", true);
            planeNoise.start();
        }
    }

    public void updateLatLog(double latCorrente, double logCorrente, double latDestinazione, double logDestinazione) throws IOException {
        this.latCorrente = latCorrente;
        this.logCorrente = logCorrente;
        this.latDestinazione = latDestinazione;
        this.logDestinazione = logDestinazione;
        weather.updateAll(this.latCorrente, this.logCorrente, latDestinazione, logDestinazione);
        mappaLaterale.nuovoVolo(this.latCorrente, this.logCorrente, latDestinazione, logDestinazione);
        mappaCentrale.updateMap(velocita, this.latCorrente, this.logCorrente, false);
    }

    public void setupTastiera() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();
        // inputMap.put(KeyStroke.getKeyStroke("P"), "togglePause");
        inputMap.put(KeyStroke.getKeyStroke("8"), "directionUp");
        inputMap.put(KeyStroke.getKeyStroke("2"), "directionDown");
        inputMap.put(KeyStroke.getKeyStroke("4"), "directionLeft");
        inputMap.put(KeyStroke.getKeyStroke("6"), "directionRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), "directionUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), "directionDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), "directionLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), "directionRight");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "directionUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "directionDown");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "directionRight");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "directionLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), "speedPlus");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "speedPlus");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "speedMinus");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "speedPlus");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "speedMinus");

        actionMap.put("speedPlus", new AzionaVelocita(INCREASE));
        actionMap.put("speedMinus", new AzionaVelocita(DECREASE));
        actionMap.put("directionUp", new AzionaDirezione(UP));
        actionMap.put("directionDown", new AzionaDirezione(DOWN));
        actionMap.put("directionRight", new AzionaDirezione(RIGHT));
        actionMap.put("directionLeft", new AzionaDirezione(LEFT));

    }


    public void setAutoAtterraggio() {
        autoAtterraggio = true;
    }

    public void setDirezione(int direzione) {
        this.direzione = direzione;
        mappaLaterale.setDirezione(direzione);
    }

    public void setVelocita(int adjust) {
        velocita += adjust;
        if (velocita < 0) {
            velocita = 0;
        } else if (velocita > 69) {        // velocita massima = 690
            velocita = 69;
        }

    }

    public void update() throws IOException, InterruptedException {
        if (!pausa & !atterraggio) {
            double difference = velocita / 69.0;
            switch (direzione) {
                case UP -> latCorrente += difference;
                case DOWN -> latCorrente -= difference;
                case LEFT -> logCorrente -= difference;
                case RIGHT -> logCorrente += difference;
            }


            mappaCentrale.updateMap(velocita, latCorrente, logCorrente, false);
            weather.updateCurrent(latCorrente, logCorrente);
            mappaLaterale.updateMap(velocita, latCorrente, logCorrente);


            if (autoAtterraggio && velocita > 0) {
                raggiuntaDestinazione();
            }

        }
        repaint();
    }

    public void raggiuntaDestinazione() throws IOException, InterruptedException {
        if (Math.abs(latCorrente - latDestinazione) <= .15 && Math.abs(logCorrente - logDestinazione) <= .15) {
            mappaCentrale.updateMap(0, latDestinazione, logDestinazione, true);
            weather.updateCurrent(latDestinazione, logDestinazione);
            mappaLaterale.atterraAereo(latDestinazione, logDestinazione);
            repaint();
            if (planeNoise != null) {
                planeNoise.stopMusic();
            }
            if (suono) {
                latch = new CountDownLatch(1);
                Audio ding = new Audio(latch, 2000, "suono/ding.wav", false);
                ding.start();
                latch.await();
                latch = new CountDownLatch(1);
                landingNoise = new Audio(latch, 7000, "suono/atterraggio.wav", false);
                landingNoise.start();
            }
            landPlane();
        }
    }

    public void landPlane() throws InterruptedException {
        autoAtterraggio = false;

        velocita = 0;

        atterraggio = true;

        if (suono) {
            latch.await();
            latch = new CountDownLatch(1);
            if (suono) {
                landed = new Audio(latch, 7000, "suono/landed.wav", false);
                landed.start();
                latch.await();
            }
            if (suono) {
                latch = new CountDownLatch(1);
                cockpit = new Audio(latch, 10000, "suono/seat.wav", false);
                cockpit.start();
                latch.await();
            }
            if (suono) {
                planeNoise = new Audio(latch, 0, "suono/airTraffic.wav", true);
                planeNoise.start();
            }
        }
        atterraggio = false;    // finito suono atterraggio, si ridecolla accelerando
    }

    public void pulsantePlay() throws InterruptedException {
        pausa = !pausa;
        menu.togglePauseText();
    }

    public void pulsanteMuto() {
        if (suono) {
            cockpit.stopMusic();
            if (planeNoise != null) {
                planeNoise.stopMusic();
            }
            if (landed != null) {
                landed.stopMusic();
            }
            if (landingNoise != null) {
                landingNoise.stopMusic();
            }
            this.suono = false;
        } else {
            suono = true;
            planeNoise = new Audio(latch, 0, "suono/airTraffic.wav", true);
            planeNoise.start();
        }
    }

    @SuppressWarnings("deprecation")
    public void apriIstruzioni() {
        inst.show();
    }


    private class AzionaDirezione extends AbstractAction {
        @Serial
        private static final long serialVersionUID = 1L;
        private final int direzione;

        public AzionaDirezione(int direzione) {
            this.direzione = direzione;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            setDirezione(direzione);
        }
    }

    private class AzionaVelocita extends AbstractAction {
        @Serial
        private static final long serialVersionUID = 1L;
        private final int velocita;

        public AzionaVelocita(int velocita) {
            this.velocita = velocita;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            setVelocita(velocita);
        }
    }
}