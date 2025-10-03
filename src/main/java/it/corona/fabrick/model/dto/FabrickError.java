package it.corona.fabrick.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FabrickError {
    private String code;
    private String description;
    private String params;
}
