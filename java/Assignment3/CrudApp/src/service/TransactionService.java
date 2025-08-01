package service;

import entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    String addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    Transaction getTransaction(Integer transactionID);
    List<Transaction> getTransactionsByCategory(String categoryName);
    List<Transaction> getTransactionsByProduct(String productName);
    List<Transaction> getTransactionsByDate(LocalDateTime localDateTime);
    String updateTransaction(Transaction transaction);
    String deleteTransaction(Integer transactionId);
}
