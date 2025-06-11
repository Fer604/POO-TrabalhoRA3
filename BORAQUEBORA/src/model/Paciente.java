package model;

public class Paciente extends Pessoa {
    private int idade;

    public Paciente(String nome, String cpf, int idade) {
        super(nome, cpf);
        this.idade = idade;
    }

    public int getIdade() { return idade; }
    @Override public String getTipo() { return "Paciente"; }
}