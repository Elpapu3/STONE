package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class NivelDino extends JPanel {
    private Jugador jugador;
    private ArrayList<Rectangle> obstaculos;
    private ArrayList<Enemigo> enemigos;
    private Timer timer;
    private final int ANCHO = 800, ALTO = 300, TAM = 40;
    private final Random rand = new Random();

    private int puntos = 0;
    private int velocidad = 6;
    private boolean saltando = false;
    private int yInicial;
    private int velocidadSalto = 15;
    private int gravedad = 5;
    private int saltoActual = 0;
    private int tiempoDesdeUltimoObstaculo = 0;
    private int tiempoDesdeUltimoEnemigo = 0;

    public NivelDino() {
        jugador = new Jugador(50, ALTO - TAM - 10, TAM, Color.GREEN);
        yInicial = jugador.getY();
        obstaculos = new ArrayList<>();
        enemigos = new ArrayList<>();

        setFocusable(true);
        setBackground(Color.WHITE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_SPACE && !saltando) {
                    saltando = true;
                }
            }
        });

        timer = new Timer(30, e -> actualizar());
        timer.start();
    }

    public boolean isSaltando() { return saltando; }
    public void setSaltando(boolean saltar) { saltando = saltar; }

    private void actualizar() {
        puntos++;
        tiempoDesdeUltimoObstaculo++;
        tiempoDesdeUltimoEnemigo++;

        if (tiempoDesdeUltimoObstaculo > 60 && rand.nextInt(100) < 10) {
            int altura = TAM + rand.nextInt(30);
            obstaculos.add(new Rectangle(ANCHO, ALTO - altura - 10, TAM, altura));
            tiempoDesdeUltimoObstaculo = 0;
        }

        if (tiempoDesdeUltimoEnemigo > 150 && rand.nextInt(100) < 7) {
            Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            enemigos.add(new Enemigo(ANCHO, ALTO - TAM - 10, TAM));
            tiempoDesdeUltimoEnemigo = 0;
        }

        Iterator<Rectangle> itObs = obstaculos.iterator();
        while (itObs.hasNext()) {
            Rectangle obs = itObs.next();
            obs.x -= velocidad;
            if (obs.x + obs.width < 0) itObs.remove();
            if (obs.intersects(jugador.getRect())) {
                System.out.println("💥 ¡Chocaste con obstáculo! Puntos: " + puntos);
                gameOver();
            }
        }

        Iterator<Enemigo> itE = enemigos.iterator();
        while (itE.hasNext()) {
            Enemigo ene = itE.next();
            int dx = jugador.getX() - ene.getX();
            int dy = jugador.getY() - ene.getY();

            int stepX = (dx > 0) ? 2 : -2;
            if (Math.abs(dx) < 3) stepX = 0;

            int stepY = (dy > 0) ? 2 : -2;
            if (Math.abs(dy) < 3) stepY = 0;

            ene.mover(stepX, new ArrayList<>(), jugador);
            ene.moverVertical(stepY);

            if (ene.getX() + TAM < 0) itE.remove();

            if (ene.getRect().intersects(jugador.getRect())) {
                System.out.println("👾 ¡Un enemigo te atrapó! Puntos: " + puntos);
                gameOver();
            }
        }

        if (saltando) {
            jugador.moverVertical(-velocidadSalto);
            saltoActual += velocidadSalto;
            if (saltoActual >= 100) {
                saltando = false;
            }
        } else if (jugador.getY() < yInicial) {
            jugador.moverVertical(gravedad);
        } else {
            saltoActual = 0;
        }

        repaint();
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "☠️ Game Over\nPuntos finales: " + puntos, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Suelo
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, ALTO - 10, ANCHO, 10);

        // Obstáculos
        g.setColor(Color.DARK_GRAY);
        for (Rectangle obs : obstaculos)
            g.fillRect(obs.x, obs.y, obs.width, obs.height);

        for (Enemigo e : enemigos)
            e.dibujar(g);

        jugador.dibujar(g);

        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntos: " + puntos, 10, 25);
    }

    public void stopTimer() {
        if (timer != null) timer.stop();
    }
}
