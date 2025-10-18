package Clases;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Juego {
    private static JFrame ventana;
    private static NivleBomb nivelBomberman;
    private static NivelDino nivelDino;
    private static boolean enNivelDino = false;
    private static boolean cambioHecho = false; 
    public static void main(String[] args) {
        ventana = new JFrame("Juego");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        nivelBomberman = new NivleBomb();
        ventana.setContentPane(nivelBomberman);
        ventana.setVisible(true);

        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_P && !enNivelDino && !cambioHecho) {
                    cambioHecho = true; 
                    enNivelDino = true;
                    System.out.println("Cambio de nivel");

                    nivelBomberman.stopTimer();

                    SwingUtilities.invokeLater(() -> {
                        nivelDino = new NivelDino();
                        ventana.setContentPane(nivelDino);
                        ventana.revalidate();
                        ventana.repaint();
                        nivelDino.requestFocusInWindow();
                        System.out.println("Nivel Dino cargado");
                    });
                }

                if (enNivelDino && nivelDino != null) {
                    if (key == KeyEvent.VK_SPACE && !nivelDino.isSaltando()) {
                        nivelDino.setSaltando(true);
                    }
                }
            }
        });
    }
}
