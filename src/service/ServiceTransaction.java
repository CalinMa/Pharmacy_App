package service;

import domain.Drug;
import domain.Transaction;
import domain.TransactionValidator;
import repository.IRepository;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransaction {
    private IRepository<Transaction> repositoryTransaction;
    private IRepository<Drug> repository;
    private TransactionValidator transactionValidator;

    public ServiceTransaction(IRepository<Transaction> repositoryTransaction,
                              IRepository<Drug> repository,
                              TransactionValidator transactionValidator) {
        this.repositoryTransaction = repositoryTransaction;
        this.repository = repository;
        this.transactionValidator = transactionValidator;
    }

    // Tranzacțion can be performed only if:
    //		number of drugs > number of transactions.
    public void addTransaction(int transactionId, int drugId, int clientCardNumber, int numberOfDrugItems, String dateAndHour) throws Exception {
        Transaction transaction = new Transaction(transactionId, drugId, clientCardNumber, numberOfDrugItems, dateAndHour);
        this.transactionValidator.validate(transaction, this.repository);
        this.repositoryTransaction.create(transaction);
    }

    public void updateTransaction(int transactionId, int drugId, int clientCardNumber, int numberOfTransactions, String dateAndHour) throws Exception {
        Transaction transaction = new Transaction(transactionId, drugId, clientCardNumber, numberOfTransactions, dateAndHour);
        this.transactionValidator.validate(transaction, this.repository);
        this.repositoryTransaction.update(transaction);
    }

    public void delete(int transactionId) throws Exception {
        this.repositoryTransaction.delete(transactionId);
    }

    public List<Transaction> getAll() {
        return this.repositoryTransaction.read();
    }

    public List<Transaction> searchTransaction(String searchText) {
        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : this.getAll()) {
            if (transaction.getDateAndHour().contains(searchText) ||
                    String.valueOf(transaction.getIdEntity()).contains(searchText) ||
                    String.valueOf(transaction.getDrugId()).contains(searchText) ||
                    String.valueOf(transaction.getClientCardNumber()).contains(searchText) ||
                    String.valueOf(transaction.getNumberOfTransactions()).contains(searchText)) {
                results.add(transaction);
            }
        }

        return results;
    }

    /**
     * Return all the transactions between two dates
     * @param start start date
     * @param end end date
     * @return list with all the transactions with date between start and end
     */
    public List<Transaction> getBetweenTwoDateAndTimes(LocalDate start, LocalDate end) {
        List<Transaction> results = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Transaction t: this.getAll()){
            String stringDate = t.getDateAndHour();
            LocalDate typedDate = LocalDate.parse(stringDate, formatter);
            if (start.isBefore(typedDate) && typedDate.isBefore(end)){
                results.add(t);
            }
        }
        return results;
    }
}
