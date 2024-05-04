package com.nikolaslouret.apiclinicamedica.models;

import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String name;
    private String surname;
    private char gender;
    private LocalDate birthDate;
    private byte age;
    private short height;
    private double weight;
    private String cpf;
    private double imc;

    public Patient(PatientDTO dto) {
        this.name = dto.name();
        this.surname = dto.surname();
        this.gender = dto.gender();
        this.birthDate = dto.birthDate();
        this.cpf = dto.cpf();
        this.weight = dto.weight();
        this.height = dto.height();
        this.imc = this.calculateIMC();
        this.age = this.calculateAge();
    }

    public double getIdealWeight() {
        return switch (this.gender) {
            case 'F' -> (62.1 * this.height) - 44.7;
            case 'M' -> (72.7 * this.height) - 58;
            default -> throw new IllegalArgumentException("Gênero inválido.");
        };
    }

    public String getObfuscatedCPF() {
        String cpfSplit = this.cpf.substring(3, 6);

        return "***." + cpfSplit + ".***-**";
    }

    public String getIMCStatus() {
        if (this.imc < 17)
            return "Muito abaixo do peso";

        if (this.imc >= 17 && this.imc < 18.5)
            return "Abaixo do peso";

        if (this.imc >= 18.5 && this.imc < 25)
            return "Peso normal";

        if (this.imc >= 25 && this.imc < 30)
            return "Acima do peso";

        if (this.imc >= 30 && this.imc < 35)
            return "Obesidade I";

        if (this.imc >= 35 && this.imc < 40)
            return "Obesidade II (severa)";

        if (this.imc >= 40)
            return "Obesidade III (mórbida)";

        throw new IllegalArgumentException("IMC inválido");
    }

    private double calculateIMC() {
        return this.weight / Math.pow(this.height, 2);
    }

    private byte calculateAge() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(this.birthDate, today);

        return (byte) period.getYears();
    }

    private boolean validateCPF() {
        int v1 = 0, v2 = 0;
        int[] cpfNum = this.cpfToArrayNumber(this.cpf);

        for (int i = 0; i < cpfNum.length; i++) {
            v1 += cpfNum[i] * (9 - (i % 10));
            v2 += cpfNum[i] * (9 - ((i + 1) % 10));
        }

        v1 = (v1 % 11) % 10;
        v2 += v1 * 9;
        v2 = (v2 % 11) % 10;

        return v1 == cpfNum[9] && v2 == cpfNum[10];
    }

    private int[] cpfToArrayNumber(String cpf) {
        return cpf.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .toArray();
    }
}
