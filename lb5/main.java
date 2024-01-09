package lb5;
import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.createAccount("John Doe", 1000);
        bank.createAccount("Jane Smith", 500);

        try {
            BankAccount johnAccount = bank.findAccount(1);
            BankAccount janeAccount = bank.findAccount(2);

            System.out.println("Before transfer:");
            System.out.println(johnAccount.getAccountSummary());
            System.out.println(janeAccount.getAccountSummary());

            bank.transferMoney(1, 2, 200);

            System.out.println("\nAfter transfer:");
            System.out.println(johnAccount.getAccountSummary());
            System.out.println(janeAccount.getAccountSummary());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class BankAccount {
    private int accountNumber;
    private String accountName;
    private double balance;

    public BankAccount(int accountNumber, String accountName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = initialDeposit;
    }

    public void deposit(double amount) throws NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException("Deposit amount cannot be negative");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws NegativeAmountException, InsufficientFundsException {
        if (amount < 0) {
            throw new NegativeAmountException("Withdrawal amount cannot be negative");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountSummary() {
        return "Account Number: " + accountNumber + "\nAccount Name: " + accountName + "\nBalance: " + balance;
    }
}

class Bank {
    private Map<Integer, BankAccount> accounts = new HashMap<>();

    public void createAccount(String accountName, double initialDeposit) {
        int accountNumber = accounts.size() + 1;
        BankAccount newAccount = new BankAccount(accountNumber, accountName, initialDeposit);
        accounts.put(accountNumber, newAccount);
    }

    public BankAccount findAccount(int accountNumber) throws AccountNotFoundException {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with account number: " + accountNumber);
        }
        return account;
    }

    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount)
            throws NegativeAmountException, InsufficientFundsException, AccountNotFoundException {
        BankAccount fromAccount = findAccount(fromAccountNumber);
        BankAccount toAccount = findAccount(toAccountNumber);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class NegativeAmountException extends Exception {
    public NegativeAmountException(String message) {
        super(message);
    }
}

class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}



