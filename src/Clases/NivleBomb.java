package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class NivleBomb extends JPanel {

    private Jugador jugador;
    private ArrayList<Rectangle> murosIndestructibles;
    private ArrayList<Rectangle> murosDestructibles;
    private ArrayList<Bomba> bombas;

    private Timer timer;

    public NivleBomb() {
        murosIndestructibles = new ArrayList<>();
        murosDestructibles = new ArrayList<>();
        bombas = new ArrayList<>();

        // 🔹 Crear bordes del mapa
        for (int i = 0; i < 800; i += 40) {
            murosIndestructibles.add(new Rectangle(i, 0, 40, 40));
            murosIndestructibles.add(new Rectangle(i, 560, 40, 40));
        }
        for (int j = 0; j < 600; j += 40) {
            murosIndestructibles.add(new Rectangle(0, j, 40, 40));
            murosIndestructibles.add(new Rectangle(760, j, 40, 40));
        }

        // 🔹 Muros internos (indestructibles)
        for (int i = 80; i < 800 - 80; i += 80) {
            for (int j = 80; j < 600 - 80; j += 80) {
                murosIndestructibles.add(new Rectangle(i, j, 40, 40));
            }
        }

        // 🔹 Muros destructibles aleatorios
        for (int i = 40; i < 760; i += 40) {
            for (int j = 40; j < 560; j += 40) {
                if (Math.random() < 0.25 && !colisionaConMuroFijo(i, j)) {
                    murosDestructibles.add(new Rectangle(i, j, 40, 40));
                }
            }
        }

        // 🔹 Crear jugador en la esquina superior izquierda libre
        Point spawn = encontrarPosicionLibre();
        jugador = new Jugador(spawn.x, spawn.y, 40, Color.RED);

        // 🔹 Configuración visual
        setFocusable(true);
        setBackground(new Color(180, 180, 180));

        // 🔹 Crear y agregar el listener de teclado
        Teclado teclado = new Teclado(jugador, 800, 600, getTodosMuros(), this);
        addKeyListener(teclado);

        // 🔹 Timer para actualizar bombas
        timer = new Timer(100, e -> actualizar());
        timer.start();

        // 🔹 Asegurar que el panel reciba el foco cuando se muestre
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
            System.out.println("🟢 Foco establecido en NivleBomb");
        });
    }

    // 🔹 Buscar posición libre para spawn
    private Point encontrarPosicionLibre() {
        for (int x = 40; x < 760; x += 40) {
            for (int y = 40; y < 560; y += 40) {
                Rectangle posible = new Rectangle(x, y, 40, 40);
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
        // fallback por si no hay espacio
        return new Point(60, 60);
    }

    private ArrayList<Rectangle> getTodosMuros() {
        ArrayList<Rectangle> todos = new ArrayList<>();
        todos.addAll(murosIndestructibles);
        todos.addAll(murosDestructibles);
        return todos;
    }

    private boolean colisionaConMuroFijo(int x, int y) {
        Rectangle nuevo = new Rectangle(x, y, 40, 40);
        for (Rectangle m : murosIndestructibles) {
            if (m.intersects(nuevo)) return true;
        }
        return false;
    }

    private void colocarBomba() {
        for (Bomba b : bombas) {
            if (b.getRect().intersects(jugador.getRect())) return;
        }
        bombas.add(new Bomba(jugador.getX(), jugador.getY()));
    }

    private void actualizar() {
        Iterator<Bomba> it = bombas.iterator();
        while (it.hasNext()) {
            Bomba b = it.next();
            b.actualizar();

            if (b.estaExplotando()) {
                Iterator<Rectangle> mit = murosDestructibles.iterator();
                while (mit.hasNext()) {
                    Rectangle muro = mit.next();
                    if (b.getExplosionArea().intersects(muro)) {
                        mit.remove();
                    }
                }
            }

            if (b.finalizo()) {
                it.remove();
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 🔹 Muros indestructibles
        g.setColor(Color.DARK_GRAY);
        for (Rectangle muro : murosIndestructibles) {
            g.fillRect(muro.x, muro.y, muro.width, muro.height);
        }

        // 🔹 Muros destructibles
        g.setColor(new Color(139, 69, 19)); // Marrón
        for (Rectangle muro : murosDestructibles) {
            g.fillRect(muro.x, muro.y, muro.width, muro.height);
        }

        // 🔹 Bombas
        for (Bomba b : bombas) {
            b.dibujar(g);
        }

        // 🔹 Jugador
        jugador.dibujar(g);
    }
}
