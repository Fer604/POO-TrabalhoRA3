package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class Tela extends JPanel {
    private static final int ALTURA_BARRA_TITULO = 20;
    private JFrame frame;
    public Tela(String nome) {
        setFocusable(true);
        frame = new JFrame(nome); // cria um frame; // insere o território no frame
        frame.setSize(400, 300 + ALTURA_BARRA_TITULO ); // define as dimensões do frame
        frame.setLocationRelativeTo(null);//centraliza a janela no ... centro
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define como o frame é fechado


        JPanel painel = new JPanel();
        painel.setLayout(null);

        inserir_componentes(painel);
        frame.add(painel);
    }
    public void mostrar() {
        frame.setVisible(true);// torna o frame visível
    }
    private static void inserir_componentes(JPanel painel) {
        /// System.out.println("\nEscolha a interface:");
        ///             System.out.println("(1) Interface do Médico");
        ///             System.out.println("(2) Interface do SistemaESeusObjetos.Paciente");
        ///             System.out.println("(9) Sair");



        /*JLabel rotuloUsuario = new JLabel("Usuário:");
        rotuloUsuario.setBounds(10, 10, 80, 25);
        painel.add(rotuloUsuario);

        JTextField campoUsuario = new JTextField(20);
        campoUsuario.setBounds(100, 10, 160, 25);
        painel.add(campoUsuario);*/


        JButton botaoInterMedic = new JButton("Interface do Médico");
        botaoInterMedic.setBounds(125, 65, 150, 25);
        painel.add(botaoInterMedic);

        JButton botaoInterPacient = new JButton("Interface do Paciente");
        botaoInterPacient.setBounds(120, 100, 160, 25);
        painel.add(botaoInterPacient);

        JButton sair = new JButton("Sair");
        sair.setBounds(150, 155, 100, 25);
        painel.add(sair);



        ActionListener leitorBotoes = new LeitorBotoes();
        botaoInterMedic.addActionListener(leitorBotoes);
        botaoInterPacient.addActionListener(leitorBotoes);
        sair.addActionListener(leitorBotoes);
    }
}