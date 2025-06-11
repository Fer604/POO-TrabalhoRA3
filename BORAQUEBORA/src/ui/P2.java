package ui;

import model.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class P2 extends JFrame {
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;
    private JTextArea resultado;

    public P2() throws Exception {
        medicos = (List<Medico>) carregar("medicos.dat");
        pacientes = (List<Paciente>) carregar("pacientes.dat");
        consultas = (List<Consulta>) carregar("consultas.dat");

        setTitle("Sistema de Consultas Médicas");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 2));
        JTextField cpfField = new JTextField();
        JButton buscarMedico = new JButton("Buscar Médico");
        JButton buscarPaciente = new JButton("Buscar Paciente");

        resultado = new JTextArea();
        resultado.setEditable(false);

        buscarMedico.addActionListener(e -> buscarPorCpf(cpfField.getText(), true));
        buscarPaciente.addActionListener(e -> buscarPorCpf(cpfField.getText(), false));

        painel.add(new JLabel("CPF:"));
        painel.add(cpfField);
        painel.add(buscarMedico);
        painel.add(buscarPaciente);

        add(painel, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        setVisible(true);
    }

    private void buscarPorCpf(String cpf, boolean isMedico) {
        try {
            if (isMedico) {
                Medico m = medicos.stream().filter(x -> x.getCpf().equals(cpf)).findFirst()
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Médico não encontrado."));
                resultado.setText(m.toString());
                salvarResultado("Médico encontrado: " + m);
            } else {
                Paciente p = pacientes.stream().filter(x -> x.getCpf().equals(cpf)).findFirst()
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente não encontrado."));
                resultado.setText(p.toString());
                salvarResultado("Paciente encontrado: " + p);
            }
        } catch (EntidadeNaoEncontradaException e) {
            resultado.setText(e.getMessage());
            salvarResultado("Erro: " + e.getMessage());
        }
    }

    private void salvarResultado(String conteudo) {
        try (FileWriter fw = new FileWriter("resultados.txt", true)) {
            fw.write(conteudo + "\n");
        } catch (IOException e) {
            resultado.append("\nErro ao salvar resultado.");
        }
    }

    private Object carregar(String nome) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nome))) {
            return ois.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        new P2();
    }
}
