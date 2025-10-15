package Clases;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Teclado extends KeyAdapter {

    private Jugador jugador;
    private int anchoVentana, altoVentana;
    private ArrayList<Rectangle> muros;
    private JPanel panel; // 🔹 referencia al panel para hacer repaint()

    public Teclado(Jugador jugador, int anchoVentana, int altoVentana, ArrayList<Rectangle> muros, JPanel panel) {
        this.jugador = jugador;
        this.anchoVentana = anchoVentana;
        this.altoVentana = altoVentana;
        this.muros = muros;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int paso = 10;

        // 🔹 Mensaje general para ver si las teclas funcionan
        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()));

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                jugador.mover(-paso, 0, muros, anchoVentana, altoVentana);
                System.out.println("← Movimiento a la izquierda");
            }
            case KeyEvent.VK_RIGHT -> {
                jugador.mover(paso, 0, muros, anchoVentana, altoVentana);
                System.out.println("→ Movimiento a la derecha");
            }
            case KeyEvent.VK_UP -> {
                jugador.mover(0, -paso, muros, anchoVentana, altoVentana);
                System.out.println("↑ Movimiento hacia arriba");
            }
            case KeyEvent.VK_DOWN -> {
                jugador.mover(0, paso, muros, anchoVentana, altoVentana);
                System.out.println("↓ Movimiento hacia abajo");
            }
            default -> {
                System.out.println("Otra tecla presionada");
            }
        }

        panel.repaint(); // 🔹 actualizar dibujo
    }
}
