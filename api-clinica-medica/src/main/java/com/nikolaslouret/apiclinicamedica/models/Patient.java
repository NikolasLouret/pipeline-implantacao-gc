package com.nikolaslouret.apiclinicamedica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do paciente é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo {max} caracteres")
    private String name;

    @NotBlank(message = "O sobrenome do paciente é obrigatório")
    @Size(max = 100, message = "O sobrenome deve ter no máximo {max} caracteres")
    private String surname;

    @NotNull(message = "O gênero do paciente é obrigatório")
    @Pattern(regexp = "[MF]", message = "O gênero deve ser 'M' para masculino ou 'F' para feminino")
    private String gender;

    @NotNull(message = "A data de nascimento do paciente é obrigatória")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @NotNull(message = "A idade do paciente é obrigatória")
    @Positive(message = "A idade do paciente deve ser um número positivo")
    private Byte age;

    @NotNull(message = "A altura do paciente é obrigatória")
    @Positive(message = "A altura do paciente deve ser um número positivo")
    private Short height;

    @NotNull(message = "O peso do paciente é obrigatório")
    @Positive(message = "O peso do paciente deve ser um número positivo")
    private Double weight;

    @JsonIgnore
    @NotBlank(message = "O CPF do paciente é obrigatório")
    @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}", message = "O CPF deve estar no formato XXX.XXX.XXX-XX")
    @Column(unique = true)
    private String cpf;

    @Positive(message = "O IMC do paciente deve ser um número positivo")
    private Double imc;

    public Patient(PatientDTO dto) {

        if (!this.validateCPF(dto.cpf()))
            throw new IllegalArgumentException("CPF Inválido");

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

    @JsonProperty("idealWeight")
    public Double getIdealWeight() {
        Double idealWeight = switch (this.gender) {
            case "F" -> (62.1 * this.height / 100) - 44.7;
            case "M" -> (72.7 * this.height / 100) - 58;
            default -> throw new IllegalArgumentException("Gênero inválido.");
        };

        return Math.round(idealWeight * 100.0) / 100.0;
    }

    @JsonProperty("cpf")
    public String getObfuscatedCPF() {
        String cpfSplit = this.cpf.substring(4, 7);

        return "***." + cpfSplit + ".***-**";
    }

    @JsonProperty("IMCStatus")
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

    private Double calculateIMC() {
        double imc = this.weight / Math.pow((double) this.height / 100, 2);
        return Math.round(imc * 100.0) / 100.0;
    }

    private Byte calculateAge() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(this.birthDate, today);

        return (byte) period.getYears();
    }

    private Boolean validateCPF(String cpf) {
        int v1 = 0, v2 = 0;
        int[] cpfNum = this.cpfToArrayNumber(cpf);

        for (int i = 0; i < 9; i++) {
            v1 += cpfNum[i] * (9 - (i % 10));
            v2 += cpfNum[i] * (9 - ((i + 1) % 10));
        }

        v1 = (v1 % 11) % 10;
        v2 += v1 * 9;
        v2 = (v2 % 11) % 10;

        return v1 == cpfNum[10] && v2 == cpfNum[9];
    }

    private int[] cpfToArrayNumber(String cpf) {
        return cpf.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .toArray();
    }
}
