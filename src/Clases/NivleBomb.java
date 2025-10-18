package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class NivleBomb extends JPanel {
    private Jugador jugador;
    private ArrayList<Rectangle> murosIndestructibles, murosDestructibles;
    private ArrayList<Bomba> bombas;
    private ArrayList<Enemigo> enemigos;
    private Timer timer;
    private final int ANCHO = 800, ALTO = 600, TAM = 40;
    private final Random rand = new Random();

    private int puntos = 0;
    private int vidas = 3;

    public NivleBomb() {
        murosIndestructibles = new ArrayList<>();
        murosDestructibles = new ArrayList<>();
        bombas = new ArrayList<>();
        enemigos = new ArrayList<>();

        generarMapa();
        jugador = new Jugador(40, 40, TAM, Color.RED);

        for (int i = 0; i < 6; i++) {
            Point p = encontrarPosicionLibre();
            enemigos.add(new Enemigo(p.x, p.y, TAM));
        }

        setFocusable(true);
        addKeyListener(new Teclado(jugador, this));

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & e.SHOWING_CHANGED) != 0 && isShowing()) {
                requestFocusInWindow();
            }
        });

        timer = new Timer(50, e -> actualizar());
        timer.start();
    }

    private void generarMapa() {
        for (int i = 0; i < ANCHO; i += TAM) {
            murosIndestructibles.add(new Rectangle(i, 0, TAM, TAM));
            murosIndestructibles.add(new Rectangle(i, ALTO - TAM, TAM, TAM));
        }
        for (int j = 0; j < ALTO; j += TAM) {
            murosIndestructibles.add(new Rectangle(0, j, TAM, TAM));
            murosIndestructibles.add(new Rectangle(ANCHO - TAM, j, TAM, TAM));
        }

        for (int i = TAM * 2; i < ANCHO - TAM * 2; i += TAM * 2)
            for (int j = TAM * 2; j < ALTO - TAM * 2; j += TAM * 2)
                murosIndestructibles.add(new Rectangle(i, j, TAM, TAM));

        for (int i = TAM; i < ANCHO - TAM; i += TAM)
            for (int j = TAM; j < ALTO - TAM; j += TAM)
                if (Math.random() < 0.25 && !colisionaConMuroFijo(i, j))
                    murosDestructibles.add(new Rectangle(i, j, TAM, TAM));
    }

    private boolean colisionaConMuroFijo(int x, int y) {
        Rectangle nuevo = new Rectangle(x, y, TAM, TAM);
        for (Rectangle m : murosIndestructibles)
            if (m.intersects(nuevo)) return true;
        return false;
    }

    public ArrayList<Rectangle> getTodosMuros() {
        ArrayList<Rectangle> todos = new ArrayList<>();
        todos.addAll(murosIndestructibles);
        todos.addAll(murosDestructibles);
        return todos;
    }

    private Point encontrarPosicionLibre() {
        for (int x = TAM; x < ANCHO - TAM; x += TAM) {
            for (int y = TAM; y < ALTO - TAM; y += TAM) {
                Rectangle posible = new Rectangle(x, y, TAM, TAM);
                boolean libre = true;
                for (Rectangle muro : getTodosMuros()) {
                    if (muro.intersects(posible)) {
                        libre = false;
                        break;
                    }
                }
                if (libre) return new Point(x, y);
            }
        }
        return new Point(TAM, TAM);
    }

    public void colocarBomba(int x, int y) {
        for (Bomba b : bombas)
            if (b.getRect().intersects(new Rectangle(x, y, TAM, TAM))) return;

        bombas.add(new Bomba(x, y));
    }

    public Jugador getJugador() {
        return jugador;
    }


    private void actualizar() {
        if (!jugador.estaVivo() && vidas <= 0) return;

        for (Enemigo e : enemigos) {
            e.mover(TAM, getTodosMuros(), jugador);
            if (rand.nextInt(100) < 2) colocarBomba(e.getX(), e.getY());

            for (Bomba b : bombas) {
                if (b.estaExplotando() && b.getExplosionArea().intersects(jugador.getRect())) {
                    vidas--;
                    jugador.morir();
                    if (vidas > 0) {
                        jugador = new Jugador(40, 40, TAM, Color.RED);
                    } else {
                        timer.stop(); // game over
                        System.out.println("Game Over! Puntos finales: " + puntos);
                    }
                }
            }
        }

        Iterator<Bomba> it = bombas.iterator();
        while (it.hasNext()) {
            Bomba b = it.next();
            b.actualizar();

            if (b.estaExplotando()) {
                int murosAntes = murosDestructibles.size();
                murosDestructibles.removeIf(m -> b.getExplosionArea().intersects(m));
                puntos += (murosAntes - murosDestructibles.size()) * 10; 
            }

            if (b.finalizo()) it.remove();
        }

        repaint();
    }

  
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(180, 180, 180));
        g.fillRect(0, 0, ANCHO, ALTO);

        g.setColor(Color.DARK_GRAY);
        for (Rectangle m : murosIndestructibles) g.fillRect(m.x, m.y, m.width, m.height);

        g.setColor(new Color(139, 69, 19));
        for (Rectangle m : murosDestructibles) g.fillRect(m.x, m.y, m.width, m.height);

        for (Bomba b : bombas) b.dibujar(g);
        jugador.dibujar(g);

        for (Enemigo e : enemigos) e.dibujar(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntos: " + puntos, 10, 20);
        g.drawString("Vidas: " + vidas, 10, 40);
    }
    
    public void stopTimer() {
        if (timer != null) timer.stop();
    }

}
