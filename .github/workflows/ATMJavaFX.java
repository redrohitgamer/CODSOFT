package jfxsetup;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATMJavaFX extends Application {

    private BankAccount bankAccount = new BankAccount(1000.0);
    private Label balanceLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM JavaFX");

        Label titleLabel = new Label("ATM System");
        Label instructionLabel = new Label("Select an option:");

        Button checkBalanceButton = new Button("Check Balance");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button exitButton = new Button("Exit");

        balanceLabel = new Label("Balance: $" + bankAccount.getBalance());

        checkBalanceButton.setOnAction(event -> showBalance());
        depositButton.setOnAction(event -> showDepositDialog());
        withdrawButton.setOnAction(event -> showWithdrawDialog());
        exitButton.setOnAction(event -> primaryStage.close());

        VBox root = new VBox(10, titleLabel, instructionLabel, checkBalanceButton, depositButton, withdrawButton, exitButton, balanceLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showBalance() {
        balanceLabel.setText("Balance: $" + bankAccount.getBalance());
    }

    private void showDepositDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter deposit amount:");
        dialog.setContentText("Amount:");

        dialog.showAndWait().ifPresent(amount -> {
            try {
                double depositAmount = Double.parseDouble(amount);
                bankAccount.deposit(depositAmount);
                showBalance();
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid amount.");
            }
        });
    }

    private void showWithdrawDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Enter withdrawal amount:");
        dialog.setContentText("Amount:");

        dialog.showAndWait().ifPresent(amount -> {
            try {
                double withdrawalAmount = Double.parseDouble(amount);
                bankAccount.withdraw(withdrawalAmount);
                showBalance();
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid amount.");
            } catch (InsufficientBalanceException e) {
                showAlert("Insufficient balance.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) {
            balance = initialBalance;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public void withdraw(double amount) throws InsufficientBalanceException {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
            } else {
                throw new InsufficientBalanceException();
            }
        }
    }

    private class InsufficientBalanceException extends Exception {
    }
}

