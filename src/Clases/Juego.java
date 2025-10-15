package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Juego extends JPanel {

    private NivleBomb nivelBomberman;
    private NivelPvZ nivelPvZ;
    private int nivelActual = 1; // 1 = Bomberman, 2 = Tower Defense

    public Juego() {
        setFocusable(true);
        setLayout(new BorderLayout());

        // Crear los niveles
        nivelBomberman = new NivleBomb();
        nivelPvZ = new NivelPvZ();

        // Mostrar primer nivel (Bomberman)
        mostrarNivel(nivelBomberman);

        // Escuchar tecla "S" para cambiar de nivel
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cambiarNivel();
                }
            }
        });
    }

    private void mostrarNivel(JPanel nivel) {
        removeAll();
        add(nivel, BorderLayout.CENTER);
        revalidate();
        repaint();

        // 🔹 Pedir foco después de agregar el nivel al panel principal
        SwingUtilities.invokeLater(() -> {
            nivel.requestFocusInWindow();
        });
    }


    private void cambiarNivel() {
        if (nivelActual == 1) {
            nivelActual = 2;
            System.out.println("Cambiado al Nivel Tower Defense");
            mostrarNivel(nivelPvZ);
        } else {
            nivelActual = 1;
            System.out.println("Cambiado al Nivel Bomberman");
            mostrarNivel(nivelBomberman);
        }
    }

    // Método para probar el juego
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mi Juego");
        Juego juego = new Juego();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(juego);
        frame.setSize(800, 600);  // tamaño fijo para asegurarnos que se vea
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
