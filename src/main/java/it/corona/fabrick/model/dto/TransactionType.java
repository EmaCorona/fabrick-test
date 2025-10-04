package it.corona.fabrick.model.dto;

import it.corona.fabrick.enums.Enumeration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionType {
    // I only included enumeration as an Enum because
    // I'm not sure about all the values that may come in

    private Enumeration enumeration;
    private String value;
}
