package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Consulta implements Serializable {
    private Medico medico;
    private Paciente paciente;
    private LocalDate data;

    public Consulta(Medico medico, Paciente paciente, LocalDate data) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
    }

    public Medico getMedico() { return medico; }
    public Paciente getPaciente() { return paciente; }
    public LocalDate getData() { return data; }

    @Override
    public String toString() {
        return "Consulta em " + data + ": " + medico.getNome() + " com " + paciente.getNome();
    }
}