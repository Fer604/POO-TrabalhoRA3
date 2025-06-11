package model;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + ": " + nome + " (" + cpf + ")";
    }
}