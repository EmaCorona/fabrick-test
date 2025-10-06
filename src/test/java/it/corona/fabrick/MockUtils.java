package it.corona.fabrick;


import it.corona.fabrick.enums.Enumeration;
import it.corona.fabrick.model.dto.*;
import it.corona.fabrick.model.request.PaymentRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class MockUtils {

    private MockUtils() {
        super();
    }

    public static BankAccount getMockedBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId("14537780");
        bankAccount.setIban("IT40L0326822311052923800661");
        bankAccount.setAbiCode("03268");
        bankAccount.setCabCode("22311");
        bankAccount.setCountryCode("IT");
        bankAccount.setInternationalCin("40");
        bankAccount.setNationalCin("L");
        bankAccount.setAccount("52923800661");
        bankAccount.setAlias("Test api");
        bankAccount.setProductName("Conto Websella");
        bankAccount.setHolderName("VALERIAS VELERIA ALBERTARIO ALBERTO");
        bankAccount.setActivatedDate(LocalDate.parse("2016-12-14"));
        bankAccount.setCurrency("EUR");
        return bankAccount;
    }

    public static Balance getMockedBalance() {
        Balance balance = new Balance();
        balance.setDate(LocalDate.parse("2025-10-06"));
        balance.setBalance(BigDecimal.valueOf(-18.80));
        balance.setAvailableBalance(BigDecimal.valueOf(-18.80));
        balance.setCurrency("EUR");
        return balance;
    }

    public static TransactionPayload getMockedTransactionPayload() {
        TransactionType type1 = new TransactionType();
        type1.setEnumeration(Enumeration.GBS_TRANSACTION_TYPE);
        type1.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0050");

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("282831");
        transaction1.setOperationId("00000000282831");
        transaction1.setAccountingDate(LocalDate.parse("2019-11-29"));
        transaction1.setValueDate(LocalDate.parse("2019-12-01"));
        transaction1.setType(type1);
        transaction1.setAmount(BigDecimal.valueOf(-343.77));
        transaction1.setCurrency("EUR");
        transaction1.setDescription("PD VISA CORPORATE 10");

        TransactionType type2 = new TransactionType();
        type2.setEnumeration(Enumeration.GBS_TRANSACTION_TYPE);
        type2.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0010");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("1460159524001");
        transaction2.setOperationId("19000191134336");
        transaction2.setAccountingDate(LocalDate.parse("2019-11-11"));
        transaction2.setValueDate(LocalDate.parse("2019-11-09"));
        transaction2.setType(type2);
        transaction2.setAmount(BigDecimal.valueOf(854.00));
        transaction2.setCurrency("EUR");
        transaction2.setDescription("BD LUCA TERRIBILE        DA 03268.49130         DATA ORDINE 09112019 COPERTURA VISA");

        TransactionPayload payload = new TransactionPayload();
        payload.setList(List.of(transaction1, transaction2));

        return payload;
    }

    public static MoneyTransfer getMockedMoneyTransfer() {
        MoneyTransfer moneyTranfer = new MoneyTransfer();
        moneyTranfer.setMoneyTransferId("452516859427");
        moneyTranfer.setStatus("EXECUTED");
        moneyTranfer.setDirection("OUTGOING");

        Creditor creditor = new Creditor();
        creditor.setName("John Doe");

        Account creditorAccount = new Account();
        creditorAccount.setAccountCode("IT23A0336844430152923804660");
        creditorAccount.setBicCode("SELBIT2BXXX");
        creditor.setAccount(creditorAccount);

        Address address = new Address();
        creditor.setAddress(address);

        Debtor debtor = new Debtor();
        debtor.setName("");
        Account debtorAccount = new Account();
        debtorAccount.setAccountCode("IT61F0326802230280596327270");
        debtorAccount.setBicCode(null);
        debtor.setAccount(debtorAccount);

        moneyTranfer.setCreditor(creditor);
        moneyTranfer.setDebtor(debtor);
        moneyTranfer.setCro("1234566788907");
        moneyTranfer.setUri("REMITTANCE_INFORMATION");
        moneyTranfer.setTrn("AJFSAD1234566788907CCSFDGTGVGV");
        moneyTranfer.setDescription("Description");
        moneyTranfer.setCreatedDatetime(OffsetDateTime.parse("2019-04-10T10:38:55.949+02:00"));
        moneyTranfer.setAccountedDatetime(OffsetDateTime.parse("2019-04-10T10:38:56+02:00"));
        moneyTranfer.setDebtorValueDate(LocalDate.parse("2019-04-10"));
        moneyTranfer.setCreditorValueDate(LocalDate.parse("2019-04-10"));

        Amount amount = new Amount();
        amount.setDebtorAmount(BigDecimal.valueOf(800));
        amount.setDebtorCurrency("EUR");
        amount.setCreditorAmount(BigDecimal.valueOf(800));
        amount.setCreditorCurrency("EUR");
        amount.setCreditorCurrencyDate(LocalDate.parse("2019-04-10"));
        amount.setExchangeRate(1L);
        moneyTranfer.setAmount(amount);

        moneyTranfer.setIsUrgent(false);
        moneyTranfer.setIsInstant(false);
        moneyTranfer.setFeeType("SHA");
        moneyTranfer.setFeeAccountId("12345678");

        Fee fee1 = new Fee();
        fee1.setFeeCode("MK001");
        fee1.setDescription("Money transfer execution fee");
        fee1.setAmount(BigDecimal.valueOf(0.25));
        fee1.setCurrency("EUR");

        Fee fee2 = new Fee();
        fee2.setFeeCode("MK003");
        fee2.setDescription("Currency exchange fee");
        fee2.setAmount(BigDecimal.valueOf(3.5));
        fee2.setCurrency("EUR");

        moneyTranfer.setFees(List.of(fee1, fee2));
        moneyTranfer.setHasTaxRelief(true);

        return moneyTranfer;
    }

    public static PaymentRequest getMockedPaymentRequest() {
        Account creditorAccount = new Account();
        creditorAccount.setAccountCode("IT23A0336844430152923804660");
        creditorAccount.setBicCode("SELBIT2BXXX");

        Address creditorAddress = new Address();
        creditorAddress.setAddress(null);
        creditorAddress.setCity(null);
        creditorAddress.setCountryCode(null);

        Creditor creditor = new Creditor();
        creditor.setName("John Doe");
        creditor.setAccount(creditorAccount);
        creditor.setAddress(creditorAddress);

        NaturalPersonBeneficiary naturalPersonBeneficiary = new NaturalPersonBeneficiary();
        naturalPersonBeneficiary.setFiscalCode1("MRLFNC81L04A859L");
        naturalPersonBeneficiary.setFiscalCode2(null);
        naturalPersonBeneficiary.setFiscalCode3(null);
        naturalPersonBeneficiary.setFiscalCode4(null);
        naturalPersonBeneficiary.setFiscalCode5(null);

        LegalPersonBeneficiary legalPersonBeneficiary = new LegalPersonBeneficiary();
        legalPersonBeneficiary.setFiscalCode("MRLFNC81L04A859L");
        legalPersonBeneficiary.setLegalRepresentativeFiscalCode(null);

        TaxRelief taxRelief = new TaxRelief();
        taxRelief.setTaxReliefId("L449");
        taxRelief.setIsCondoUpgrade(false);
        taxRelief.setCreditorFiscalCode("56258745832");
        taxRelief.setBeneficiaryType("NATURAL_PERSON");
        taxRelief.setNaturalPersonBeneficiary(naturalPersonBeneficiary);
        taxRelief.setLegalPersonBeneficiary(legalPersonBeneficiary);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCreditor(creditor);
        paymentRequest.setExecutionDate(LocalDate.parse("2019-04-01"));
        paymentRequest.setUri("REMITTANCE_INFORMATION");
        paymentRequest.setDescription("Payment invoice 75/2017");
        paymentRequest.setAmount(BigDecimal.valueOf(800));
        paymentRequest.setCurrency("EUR");
        paymentRequest.setIsUrgent(false);
        paymentRequest.setIsInstant(false);
        paymentRequest.setFeeType("SHA");
        paymentRequest.setFeeAccountId("45685475");
        paymentRequest.setTaxRelief(taxRelief);

        return paymentRequest;
    }
}
