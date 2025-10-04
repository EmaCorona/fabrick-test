package it.corona.fabrick.mapper;

import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "externalId", source = "dto.transactionId")
    @Mapping(target = "enumeration", source = "dto.type.enumeration")
    @Mapping(target = "value", source = "dto.type.value")
    TransactionEntity toEntity(Transaction dto, Long accountId);
}