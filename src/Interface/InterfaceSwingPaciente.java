package Interface;

import SistemaESeusObjetos.Paciente;
import SistemaESeusObjetos.Sistema;
import Excecoes.ConsultaNaoEncontradaException;
import Excecoes.PacienteNaoEncontradoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceSwingPaciente {
    private JFrame frame;
    private Sistema sistema;

    public InterfaceSwingPaciente(Sistema sistema) {
        this.sistema = sistema;
        frame = new JFrame("Interface do Paciente");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("Funções do Paciente:");
        titulo.setBounds(180, 20, 200, 25);
        painel.add(titulo);

        JButton botao1 = new JButton("Ver meus dados");
        botao1.setBounds(130, 70, 240, 30);
        painel.add(botao1);

        JButton botao2 = new JButton("Ver histórico de consultas");
        botao2.setBounds(130, 120, 240, 30);
        painel.add(botao2);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(190, 200, 100, 30);
        painel.add(botaoVoltar);

        botao1.addActionListener(e -> exibirDadosPaciente());
        botao2.addActionListener(e -> exibirHistoricoConsultas());
        botaoVoltar.addActionListener(e -> {
            frame.dispose();
            Tela tela = new Tela("Sistema Médico", sistema);
            tela.mostrar();
        });

        frame.add(painel);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    private void exibirDadosPaciente() {
        String input = JOptionPane.showInputDialog("Digite o CPF do paciente:");
        if (input == null) return;

        try {
            Paciente paciente = sistema.getPacientePorCodigo(input); // método deve existir no Sistema
            StringBuilder sb = new StringBuilder();
            sb.append("Nome: ").append(paciente.getNome()).append("\n");
            sb.append("CPF: ").append(paciente.getCpf()).append("\n");
            mostrarResultado(sb.toString());
        } catch (PacienteNaoEncontradoException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void exibirHistoricoConsultas() {
        String input = JOptionPane.showInputDialog("Digite o código do paciente:");
        if (input == null) return;

        try {
            String historico = sistema.getHistoricoConsultas(input); // método deve retornar String
            mostrarResultado(historico);
        } catch (PacienteNaoEncontradoException | ConsultaNaoEncontradaException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void mostrarResultado(String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new java.awt.Dimension(400, 200));
        JOptionPane.showMessageDialog(null, scroll, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
