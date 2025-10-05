package it.corona.fabrick.model.response;

import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.model.dto.FabrickError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FabrickResponse<T> {
    private FabrickStatus status;
    private List<FabrickError> errors = new ArrayList<>();
    private T payload;
}