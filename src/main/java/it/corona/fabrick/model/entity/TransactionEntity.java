package it.corona.fabrick.model.entity;

import it.corona.fabrick.enums.Enumeration;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "operation_id", nullable = false)
    private String operationId;

    @Column(name = "accounting_date", nullable = false)
    private LocalDate accountingDate;

    @Column(name = "value_date", nullable = false)
    private LocalDate valueDate;

    @Column(name = "type_enumeration", nullable = false)
    private Enumeration enumeration;

    @Column(name = "type_value", nullable = false)
    private String value;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "account_id", nullable = false)
    private Long accountId;
}
