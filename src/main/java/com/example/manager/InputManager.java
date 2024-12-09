package com.example.manager;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputManager {

    public static Scanner scanner = new Scanner(System.in);
    private static final Pattern SELECTION_PATTERN = Pattern.compile("^[1-9]$|^1[0-4]$");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("^[*#+/]$");

    public static int readInt(String prompt) {
        OutputManager.printWithPrompt(prompt);
        while (!scanner.hasNextInt()) {
            OutputManager.print("Geçersiz giriş. Lütfen geçerli bir sayı girin.");
            OutputManager.printWithPrompt(prompt);
            scanner.next();
        }
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }

    public static int readUnrestrictedInt(String prompt) {
        OutputManager.printWithPrompt(prompt);
        while (!scanner.hasNextInt()) {
            OutputManager.print("Geçersiz giriş. Lütfen geçerli bir sayı girin.");
            OutputManager.printWithPrompt(prompt);
            scanner.next();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public static char readChar(String prompt) {
        OutputManager.printWithPrompt(prompt);
        String input = scanner.nextLine();

        while (!SYMBOL_PATTERN.matcher(input).matches()) {
            OutputManager.print("Geçersiz sembol. Lütfen *, #, + veya / karakterlerinden birini girin.");
            OutputManager.printWithPrompt(prompt);
            input = scanner.nextLine();
        }

        return input.charAt(0);
    }

    public static void closeScanner() {
        scanner.close();
    }

    public static int readValidatedSelection(String prompt) {
        OutputManager.printWithPrompt(prompt);
        String input = scanner.nextLine();

        while (!SELECTION_PATTERN.matcher(input).matches()) {
            OutputManager.print("Geçersiz seçim. Lütfen 1 ile 12 arasında bir sayı girin.");
            OutputManager.printWithPrompt(prompt);
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }
}

