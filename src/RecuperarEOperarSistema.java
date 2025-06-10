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
        //mentira foi po karai

            Tela tela = new Tela("Sistema",sistema);
            tela.mostrar();
            sc.close();


    }
}