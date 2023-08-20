package com.company;
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void main(String[] args) {
        playGuessingGame();
    }

    public static void playGuessingGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerLimit = 1;
        int upperLimit = 100;
        int maxAttempts = 10;
        int rounds = 0;
        int totalScore = 0;

        System.out.println("Welcome to the Number Guessing Game!");
        boolean playAgain = true;

        while (playAgain) {
            int targetNumber = random.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
            int attempts = 0;
            int roundScore = 0;
            rounds++;

            System.out.println("Round " + rounds + ": I'm thinking of a number between " + lowerLimit + " and " + upperLimit + ".");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the number " + targetNumber + " in " + attempts + " attempts.");
                    roundScore = maxAttempts - attempts + 1;
                    totalScore += roundScore;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Your guess is too low. Try again.");
                } else {
                    System.out.println("Your guess is too high. Try again.");
                }
            }

            if (roundScore == 0) {
                System.out.println("Round " + rounds + ": Sorry, you've reached the maximum number of attempts. The number was " + targetNumber + ".");
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("yes");
        }

        System.out.println("Game Over! You played " + rounds + " rounds and your total score is " + totalScore + " based on the number of rounds won.");
    }
}
