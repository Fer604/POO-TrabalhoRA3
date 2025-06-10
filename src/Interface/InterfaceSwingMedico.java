package Interface;

import SistemaESeusObjetos.Consulta;
import SistemaESeusObjetos.Paciente;
import SistemaESeusObjetos.Sistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class InterfaceSwingMedico {
    private JFrame frame;
    private Sistema sistema;

    public InterfaceSwingMedico(Sistema sistema) {
        this.sistema = sistema;
        frame = new JFrame("Interface do Médico");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("Funções do Médico:");
        titulo.setBounds(180, 20, 200, 25);
        painel.add(titulo);

        JButton botao1 = new JButton("Listar pacientes do médico");
        botao1.setBounds(130, 60, 240, 30);
        painel.add(botao1);

        JButton botao2 = new JButton("Consultas por período");
        botao2.setBounds(130, 110, 240, 30);
        painel.add(botao2);

        JButton botao3 = new JButton("Pacientes inativos");
        botao3.setBounds(130, 160, 240, 30);
        painel.add(botao3);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(190, 230, 100, 30);
        painel.add(botaoVoltar);

        botao1.addActionListener(e -> listarPacientesDoMedico());
        botao2.addActionListener(e -> listarConsultasPorPeriodo());
        botao3.addActionListener(e -> listarPacientesInativos());
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

    private void listarPacientesDoMedico() {
        String input = JOptionPane.showInputDialog("Digite o código do médico:");
        if (input == null) return;

        try {
            int codigo = Integer.parseInt(input);
            List<Paciente> pacientes = sistema.getPacientesDoMedico(codigo);
            StringBuilder sb = new StringBuilder("Pacientes:\n");
            for (Paciente p : pacientes) {
                sb.append("- ").append(p.getNome()).append("\n");
            }
            mostrarResultado(sb.toString());
        } catch (Exception e) {
            mostrarErro("Erro: " + e.getMessage());
        }
    }

    private void listarConsultasPorPeriodo() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código do médico:"));
            LocalDate ini = LocalDate.parse(JOptionPane.showInputDialog("Data inicial (AAAA-MM-DD):"));
            LocalDate fim = LocalDate.parse(JOptionPane.showInputDialog("Data final (AAAA-MM-DD):"));

            List<Consulta> consultas = sistema.getConsultasDoMedicoEmPeriodo(codigo, ini, fim);
            StringBuilder sb = new StringBuilder("Consultas:\n");
            for (Consulta c : consultas) {
                sb.append("- ").append(c.getData()).append(" às ").append(c.getHorario())
                        .append(" | Paciente: ").append(c.getPaciente().getNome()).append("\n");
            }
            mostrarResultado(sb.toString());
        } catch (Exception e) {
            mostrarErro("Erro ao buscar consultas.");
        }
    }

    private void listarPacientesInativos() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código do médico:"));
            int meses = Integer.parseInt(JOptionPane.showInputDialog("Número de meses de inatividade:"));
            List<Paciente> inativos = sistema.getPacientesInativos(codigo, meses);
            StringBuilder sb = new StringBuilder("Pacientes inativos:\n");
            for (Paciente p : inativos) {
                sb.append("- ").append(p.getNome()).append("\n");
            }
            mostrarResultado(sb.toString());
        } catch (Exception e) {
            mostrarErro("Erro ao buscar pacientes inativos.");
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
