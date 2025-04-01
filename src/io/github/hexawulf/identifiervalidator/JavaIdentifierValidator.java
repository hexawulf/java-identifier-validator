package io.github.hexawulf.identifiervalidator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * An interactive program to validate Java identifiers
 * for different Java elements like classes, methods, variables, etc.
 */
public class JavaIdentifierValidator {

    private static final Set<String> JAVA_KEYWORDS = new HashSet<>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum",
            "extends", "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native", "new", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "try", "void", "volatile", "while", "true", "false", "null"
    ));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Java Identifier Validator");
        System.out.println("========================");
        System.out.println("This program checks if a string is valid as a Java identifier");
        System.out.println("Type 'exit' to quit");
        System.out.println();

        while (true) {
            System.out.println("\nWhat would you like to validate?");
            System.out.println("1. Class name");
            System.out.println("2. Method name");
            System.out.println("3. Variable name");
            System.out.println("4. Package name");
            System.out.println("5. Generic identifier");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("6") || choice.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter the identifier to validate: ");
            String identifier = scanner.nextLine().trim();

            if (identifier.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                switch (choice) {
                    case "1":
                        validateClassName(identifier);
                        break;
                    case "2":
                        validateMethodName(identifier);
                        break;
                    case "3":
                        validateVariableName(identifier);
                        break;
                    case "4":
                        validatePackageName(identifier);
                        break;
                    case "5":
                        validateGenericIdentifier(identifier);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Thank you for using the Java Identifier Validator!");
        scanner.close();
    }

    private static void validateBasicIdentifier(String identifier, String type) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException(type + " cannot be empty");
        }

        if (!Character.isJavaIdentifierStart(identifier.charAt(0))) {
            throw new IllegalArgumentException(type + " must start with a letter, underscore (_), or dollar sign ($)");
        }

        for (int i = 1; i < identifier.length(); i++) {
            if (!Character.isJavaIdentifierPart(identifier.charAt(i))) {
                throw new IllegalArgumentException(type + " can only contain letters, numbers, underscores (_), or dollar signs ($)");
            }
        }

        if (JAVA_KEYWORDS.contains(identifier)) {
            throw new IllegalArgumentException(type + " cannot be a Java keyword");
        }
    }

    private static void validateClassName(String identifier) {
        validateBasicIdentifier(identifier, "Class name");

        if (!Character.isUpperCase(identifier.charAt(0))) {
            System.out.println("Warning: Class names should start with an uppercase letter (PascalCase convention)");
        }

        if (identifier.contains("_")) {
            System.out.println("Warning: Class names typically don't contain underscores");
        }

        System.out.println("'" + identifier + "' is a valid class name.");
    }

    private static void validateMethodName(String identifier) {
        validateBasicIdentifier(identifier, "Method name");

        if (!Character.isLowerCase(identifier.charAt(0))) {
            System.out.println("Warning: Method names should start with a lowercase letter (camelCase convention)");
        }

        if (identifier.contains("_")) {
            System.out.println("Warning: Method names typically don't contain underscores");
        }

        System.out.println("'" + identifier + "' is a valid method name.");
    }

    private static void validateVariableName(String identifier) {
        validateBasicIdentifier(identifier, "Variable name");

        if (!Character.isLowerCase(identifier.charAt(0))) {
            System.out.println("Warning: Variable names should start with a lowercase letter (camelCase convention)");
        }

        System.out.println("'" + identifier + "' is a valid variable name.");
    }

    private static void validatePackageName(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Package name cannot be empty");
        }

        String[] segments = identifier.split("\\.");
        for (String segment : segments) {
            validateBasicIdentifier(segment, "Package segment");
            if (!segment.equals(segment.toLowerCase())) {
                System.out.println("Warning: Package segments should be lowercase");
            }
        }

        System.out.println("'" + identifier + "' is a valid package name.");
    }

    private static void validateGenericIdentifier(String identifier) {
        validateBasicIdentifier(identifier, "Identifier");
        System.out.println("'" + identifier + "' is a valid generic identifier.");
    }
}