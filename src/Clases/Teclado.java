package Clases;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Teclado extends KeyAdapter {
    private Jugador jugador;
    private NivleBomb nivelBomb;
    private NivelDino nivelDino;
    private final int PASO = 10;

    public Teclado(Jugador jugador, NivleBomb nivel) {
        this.jugador = jugador;
        this.nivelBomb = nivel;
    }

    public Teclado(Jugador jugador, NivelDino nivel) {
        this.jugador = jugador;
        this.nivelDino = nivel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!jugador.estaVivo()) return;

        int key = e.getKeyCode();

        if (nivelBomb != null) {
            switch (key) {
                case KeyEvent.VK_W -> jugador.mover(0, -PASO, nivelBomb.getTodosMuros());
                case KeyEvent.VK_S -> jugador.mover(0, PASO, nivelBomb.getTodosMuros());
                case KeyEvent.VK_A -> jugador.mover(-PASO, 0, nivelBomb.getTodosMuros());
                case KeyEvent.VK_D -> jugador.mover(PASO, 0, nivelBomb.getTodosMuros());
                case KeyEvent.VK_SPACE -> nivelBomb.colocarBomba(jugador.getX(), jugador.getY());
            }
        }

        if (nivelDino != null) {
            if (key == KeyEvent.VK_SPACE && !nivelDino.isSaltando()) {
                nivelDino.setSaltando(true);
            }
        }

        if (key == KeyEvent.VK_P) {
            System.out.println("Cambio de nivel");
           
        }
    }
}
