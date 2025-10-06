package it.corona.fabrick.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Creditor {
    @NotBlank
    @Size(max = 70)
    private String name;
    @NotNull
    @Valid
    private Account account;
    @Valid
    private Address address;
}
