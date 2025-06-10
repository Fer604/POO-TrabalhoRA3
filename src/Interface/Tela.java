package Interface;

import SistemaESeusObjetos.Sistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JPanel {
    private JFrame frame;
    private Sistema sistema;

    public Tela(String nome, Sistema sistema) {
        this.sistema = sistema;
        frame = new JFrame(nome);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        inserirComponentes(painel);

        frame.add(painel);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    private void inserirComponentes(JPanel painel) {
        JLabel titulo = new JLabel("Escolha a interface:");
        titulo.setBounds(120, 30, 200, 25);
        painel.add(titulo);

        JButton botaoMedico = new JButton("Interface do Médico");
        botaoMedico.setBounds(100, 70, 200, 30);
        painel.add(botaoMedico);

        JButton botaoPaciente = new JButton("Interface do Paciente");
        botaoPaciente.setBounds(100, 120, 200, 30);
        painel.add(botaoPaciente);

        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(150, 180, 100, 30);
        painel.add(botaoSair);

        botaoMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                InterfaceSwingMedico medicoUI = new InterfaceSwingMedico(sistema);
                medicoUI.mostrar();
            }
        });

        botaoPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                InterfaceSwingPaciente pacienteUI = new InterfaceSwingPaciente(sistema);
                pacienteUI.mostrar();
            }
        });

        botaoSair.addActionListener(e -> System.exit(0));
    }
}
