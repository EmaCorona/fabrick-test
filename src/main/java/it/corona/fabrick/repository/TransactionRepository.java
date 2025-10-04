package it.corona.fabrick.repository;

import it.corona.fabrick.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("select t from TransactionEntity t " +
            "where t.accountId = :accountId " +
            "and t.accountingDate >= :fromAccountingDate " +
            "and t.accountingDate <= :toAccountingDate")
    List<TransactionEntity> findTransactionsByAccountIdAndDateBetween(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);
}
