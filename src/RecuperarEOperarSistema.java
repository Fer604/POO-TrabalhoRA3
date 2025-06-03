import Interface.InterfaceMedico;
import Interface.InterfacePaciente;
import Interface.LeitorBotoes;
import Interface.Tela;
import SistemaESeusObjetos.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.Scanner;

public class RecuperarEOperarSistema {
    public static void main(String[] args) {
        //inicia o objeto sistema fora do try e um novo scanner(ñ vejo motivo pra salvar scanner)
        Scanner sc = new Scanner(System.in);
        Sistema sistema = null;
        try {
            sistema = Sistema.abrir("Sistema.ser");
            System.out.println("Sistema recuperado com sucesso!");
        } catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }

        if (sistema == null) {
            System.out.println("Erro ao recuperar o sistema. Encerrando o programa.");
            sc.close();
            return;
        }
        //main antiga(mostly)



        while (true) {
            Tela tela = new Tela("tela");
            tela.rodar();
            JFrame frame = new JFrame("Login");
            frame.setSize(600, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel painel = new JPanel();
            frame.add(painel);
            inserir_componentes(painel);

            frame.setVisible(true);
            System.out.println("\nEscolha a interface:");
            System.out.println("(1) Interface do Médico");
            System.out.println("(2) Interface do Paciente");
            System.out.println("(9) Sair");
            System.out.print("Opção: ");
            int escolha = sc.nextInt();

            if (escolha == 9) break;

            switch (escolha) {
                case 1:
                    new InterfaceMedico(sc, sistema).exibirMenu();
                    break;
                case 2:
                    new InterfacePaciente(sc, sistema).exibirMenu();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }
    private static void inserir_componentes(JPanel painel) {

        painel.setLayout(null);

        // rótulos e campos


        // botões:

        //width = 600   só pra nã ter q ir lá pra cima ver os valor

        //height = 300

        JButton botao_interface_medico = new JButton("Interface Médico");
        botao_interface_medico.setBounds(getWidth()-590), 80, 150, 25);
        painel.add(botao_interface_medico);

        JButton botao_interface_paciente = new JButton("Interface Paciente");
        botao_interface_paciente.setBounds(getWidth-10, 80, 150, 25);
        painel.add(botao_interface_paciente);

        JButton botao_sair = new JButton("Sair");
        botao_sair.setBounds(300, 105, 80, 25);
        painel.add(botao_sair);

        ActionListener leitor_botoes = new LeitorBotoes();
        botao_interface_medico.addActionListener(leitor_botoes);
        botao_interface_paciente.addActionListener(leitor_botoes);
        botao_sair.addActionListener(leitor_botoes);
    }
}