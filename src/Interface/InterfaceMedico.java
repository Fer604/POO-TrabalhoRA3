package Interface;

import javax.swing.*;

import java.awt.event.ActionListener;

import SistemaESeusObjetos.Consulta;
import SistemaESeusObjetos.Paciente;
import SistemaESeusObjetos.Sistema;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class InterfaceMedico {
    private Scanner scanner;
    private Sistema sistema;
    private FileWriter writer;
    private boolean salvarEmArquivo;

    public InterfaceMedico(Scanner scanner, Sistema sistema) {
        this.scanner = scanner;
        this.sistema = sistema;
        configurarSaida();
    }

    private void configurarSaida() {
        // não mudem isso por algm motivo qnd tira a opcção para d funcionar eu tenho esdrufuncos de ideia pq
        //se pa na real se só tira o if e os prints dá boa
        System.out.println("(1) Exibir resultados na tela");
        System.out.println("(2) Salvar resultados em arquivo .txt");
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 2) {
            salvarEmArquivo = true;
            System.out.print("Digite o nome do arquivo (com .txt): ");
            String nomeArquivo = scanner.nextLine();
            try {
                writer = new FileWriter("src/" + nomeArquivo);
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo: " + e.getMessage());
                salvarEmArquivo = false;
            }
        }
    }

    public void exibirMenu() {
        while (true) {
            JFrame frame = new JFrame("Login");
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel painel = new JPanel();
            frame.add(painel);
            inserir_componentes(painel);

            frame.setVisible(true);
            System.out.println("\nInterface do Médico:");
            System.out.println("(1) Listar pacientes de um médico");
            System.out.println("(2) Consultas agendadas por período");
            System.out.println("(3) Pacientes inativos há mais de X meses");
            System.out.println("(9) Voltar");
            System.out.print("Opção: ");
            int op = scanner.nextInt();

            if (op == 9) break;

            switch (op) {
                case 1:
                    listarPacientesDoMedico();
                    break;
                case 2:
                    listarConsultasPorPeriodo();
                    break;
                case 3:
                    listarPacientesInativos();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar o arquivo.");
            }
        }
    }

    private void listarPacientesDoMedico() {
        System.out.print("Digite o código do médico: ");
        int codigo = scanner.nextInt();
        List<Paciente> pacientes = sistema.getPacientesDoMedico(codigo);
        StringBuilder resultado = new StringBuilder("Pacientes do médico " + codigo + ":\n");
        for (Paciente p : pacientes) {
            resultado.append("- ").append(p.getNome()).append("\n");
        }
        imprimirResultado(resultado.toString());
    }

    private void listarConsultasPorPeriodo() {
        System.out.print("Digite o código do médico: ");
        int codigo = scanner.nextInt();
        System.out.print("Digite a data inicial (AAAA-MM-DD): ");
        LocalDate ini = LocalDate.parse(scanner.next());
        System.out.print("Digite a data final (AAAA-MM-DD): ");
        LocalDate fim = LocalDate.parse(scanner.next());
        List<Consulta> consultas = sistema.getConsultasDoMedicoEmPeriodo(codigo, ini, fim);
        StringBuilder resultado = new StringBuilder("Consultas no período:\n");
        for (Consulta c : consultas) {
            resultado.append("- ").append(c.getData()).append(" às ").append(c.getHorario())
                    .append(", SistemaESeusObjetos.Paciente: ").append(c.getPaciente().getNome()).append("\n");
        }
        imprimirResultado(resultado.toString());
    }

    private void listarPacientesInativos() {
        System.out.print("Digite o código do médico: ");
        int codigo = scanner.nextInt();
        System.out.print("Digite o número de meses: ");
        int meses = scanner.nextInt();
        List<Paciente> inativos = sistema.getPacientesInativos(codigo, meses);
        StringBuilder resultado = new StringBuilder("Pacientes inativos:\n");
        for (Paciente p : inativos) {
            resultado.append("- ").append(p.getNome()).append("\n");
        }
        imprimirResultado(resultado.toString());
    }

    private void imprimirResultado(String texto) {
        if (salvarEmArquivo && writer != null) {
            try {
                writer.write(texto + "\n");
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo.");
            }
        } else {
            System.out.print(texto);
        }
    }
    private static void inserir_componentes(JPanel painel) {

        painel.setLayout(null);

        // rótulos e campos

        JLabel rotulo_usuario = new JLabel("Usuário");
        rotulo_usuario.setBounds(10, 10, 80, 25);
        painel.add(rotulo_usuario);

        JTextField campo_usuario = new JTextField(20);
        campo_usuario.setBounds(100, 10, 160, 25);
        painel.add(campo_usuario);

        JLabel rotulo_senha = new JLabel("Senha");
        rotulo_senha.setBounds(10, 40, 80, 25);
        painel.add(rotulo_senha);

        JPasswordField campo_senha = new JPasswordField(20);
        campo_senha.setBounds(100, 40, 160, 25);
        painel.add(campo_senha);

        // botões:

        JButton botao_login = new JButton("login");
        botao_login.setBounds(10, 80, 80, 25);
        painel.add(botao_login);

        JButton botao_cadastro = new JButton("cadastro");
        botao_cadastro.setBounds(180, 80, 80, 25);
        painel.add(botao_cadastro);

        ActionListener leitor_botoes = new LeitorBotoes();
        botao_login.addActionListener(leitor_botoes);
        botao_cadastro.addActionListener(leitor_botoes);
    }
}