package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxReliefId {
    _119R("119R", "Interventi superbonus"),
    DL50("DL50", "Sismabonus"),
    L296("L296", "Risparmio energetico"),
    L449("L449", "Ristrutturazione - Interventi art. 16-bis Tuir"),
    L234("L234", "Barriere Architettoniche");

    private final String code;
    private final String description;
}
