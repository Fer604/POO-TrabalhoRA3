package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Tela extends JPanel {
    private static final int ALTURA_BARRA_TITULO = 20;
    private JFrame frame;
    private int contador = 0;
    public Tela(String nome) {
        setFocusable(true);
        frame = new JFrame(nome); // cria um frame
        frame.add(this); // insere o território no frame
        frame.setSize(400, 300 + ALTURA_BARRA_TITULO ); // define as dimensões do frame
        frame.setVisible(true); // torna o frame visível
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define como o frame é fechado
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Contador "+String.valueOf(contador), (getWidth()/2), getHeight()/2);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(30, 60, 40, 20);
    }
    public void rodar() {
        try {
            Thread.sleep(1000); // dorme por um segundo
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        boolean rodando = true;
        while (rodando) {
            System.out.println("largura: " + getWidth() + ", altura: " + getHeight());
            contador++;
            if (contador == 69) contador = 0;
            repaint(); // atualiza a imagem da janela
            try {
                Thread.sleep(100); // dorme por décimo de segundo
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
        game_over();
    }
    private void game_over() {
        String mensagem = "Parabéns!";
        JOptionPane.showMessageDialog(this, mensagem, "Game Over", JOptionPane.YES_NO_OPTION);
    }
}