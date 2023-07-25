package Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * The EvaluateString class provides a method to evaluate mathematical expressions given as a string.
 * It supports basic arithmetic operations, logarithm, square root, exponentiation, and parentheses.
 */
public abstract class EvaluateString {

    /**
     * Evaluates the given mathematical expression with a giving value and returns the result.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluation.
     */
    public static BigDecimal evaluate(String expression) {
        expression = expression.replaceAll("pi", Double.toString(Math.PI)); // replace string Pi with its value
        expression = expression.replaceAll("\\s", ""); // Remove all whitespace characters from the expression
        ArrayList<Character> tokens = stringToCharArrayList(expression);

        // Stack for numbers: 'values'
        Stack<BigDecimal> values = new Stack<>();
        // Stack for Operators: 'ops'
        Stack<String> ops = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (i == 0 && tokens.get(i) == '-') {
                tokens.add(0, '0');
                i--;
            } else if (i > 0 && tokens.get(i) == '-' && !Character.isDigit(tokens.get(i - 1))) {
                tokens.add(i, '0');
                i--;
            }
            // Current token is a number, push it to stack for numbers
            else if (Character.isDigit(tokens.get(i)) || tokens.get(i) == '.') {
                StringBuilder sb = new StringBuilder();

                // Extract the whole number or decimal part
                while (i < tokens.size() && (Character.isDigit(tokens.get(i)) || tokens.get(i) == '.'))
                    sb.append(tokens.get(i++));
                values.push(new BigDecimal(sb.toString()));

                // Right now the 'i' points to the character next to the number,
                // Since the for loop also increases 'i', we need to decrease the value of 'i' by 1 to correct the offset.
                i--;
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens.get(i) == '(')
                ops.push(String.valueOf(tokens.get(i)));

                // Closing brace encountered, solve the entire brace
            else if (tokens.get(i) == ')') {
                while (!Objects.equals(ops.peek(), "(")) {
                    String op = ops.peek();
                    if (isLogSqrtExp(op) || isTrigonometric(op))
                        values.push(applyOp(ops.pop(), values.pop(), new BigDecimal(0)));
                    else
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }
            // Handle the log operator
            else if (tokens.get(i) == 'l' && i + 2 < tokens.size() && tokens.get(i + 1) == 'o' && tokens.get(i + 2) == 'g') {
                ops.push("log");
                i += 2;
            }
            // Handle the sqrt operator
            else if (tokens.get(i) == 's' && i + 3 < tokens.size() && tokens.get(i + 1) == 'q' && tokens.get(i + 2) == 'r' && tokens.get(i + 3) == 't') {
                ops.push("sqrt");
                i += 3;
            }
            // Handle the exp operator
            else if (tokens.get(i) == 'e' && i + 2 < tokens.size() && tokens.get(i + 1) == 'x' && tokens.get(i + 2) == 'p') {
                ops.push("exp");
                i += 2;
            }
            // Handle the sinh operator
            else if (tokens.get(i) == 's' && i + 3 < tokens.size() && tokens.get(i + 1) == 'i' && tokens.get(i + 2) == 'n' && tokens.get(i + 3) == 'h') {
                ops.push("sinh");
                i += 3;
            }
            // Handle the cosh operator
            else if (tokens.get(i) == 'c' && i + 3 < tokens.size() && tokens.get(i + 1) == 'o' && tokens.get(i + 2) == 's' && tokens.get(i + 3) == 'h') {
                ops.push("cosh");
                i += 3;
            }
            // Handle the tanh operator
            else if (tokens.get(i) == 't' && i + 3 < tokens.size() && tokens.get(i + 1) == 'a' && tokens.get(i + 2) == 'n' && tokens.get(i + 3) == 'h') {
                ops.push("tanh");
                i += 3;
            }
            // Handle the sin operator
            else if (tokens.get(i) == 's' && i + 2 < tokens.size() && tokens.get(i + 1) == 'i' && tokens.get(i + 2) == 'n') {
                ops.push("sin");
                i += 2;
            }
            // Handle the cos operator
            else if (tokens.get(i) == 'c' && i + 2 < tokens.size() && tokens.get(i + 1) == 'o' && tokens.get(i + 2) == 's') {
                ops.push("cos");
                i += 2;
            }
            // Handle the tan operator
            else if (tokens.get(i) == 't' && i + 2 < tokens.size() && tokens.get(i + 1) == 'a' && tokens.get(i + 2) == 'n') {
                ops.push("tan");
                i += 2;
            }
            // Handle the asin operator
            else if (tokens.get(i) == 'a' && i + 3 < tokens.size() && tokens.get(i + 1) == 's' && tokens.get(i + 2) == 'i' && tokens.get(i + 3) == 'n') {
                ops.push("asin");
                i += 3;
            }
            // Handle the acos operator
            else if (tokens.get(i) == 'a' && i + 3 < tokens.size() && tokens.get(i + 1) == 'c' && tokens.get(i + 2) == 'o' && tokens.get(i + 3) == 's') {
                ops.push("acos");
                i += 3;
            }
            // Handle the atan operator
            else if (tokens.get(i) == 'a' && i + 3 < tokens.size() && tokens.get(i + 1) == 't' && tokens.get(i + 2) == 'a' && tokens.get(i + 3) == 'n') {
                ops.push("atan");
                i += 3;
            }
            // Current token is an operator
            else if (tokens.get(i) == '+' || tokens.get(i) == '-' || tokens.get(i) == '*' || tokens.get(i) == '/' || tokens.get(i) == '^') {
                // While the top of 'ops' has the same or greater precedence to the current token, which is an operator.
                // Apply the operator on top of 'ops' to the top two elements in the values stack
                while (!ops.empty() && hasPrecedence(String.valueOf(tokens.get(i)), ops.peek())) {
                    String op = ops.peek();
                    if (isLogSqrtExp(op) || isTrigonometric(op))
                        values.push(applyOp(ops.pop(), values.pop(), new BigDecimal(0)));
                    else
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                // Push the current token to 'ops'
                ops.push(String.valueOf(tokens.get(i)));
            }
        }
        // Entire expression has been parsed at this point, apply remaining ops to remaining values
        while (!ops.empty()) {
            String op = ops.peek();
            if (isLogSqrtExp(op) || isTrigonometric(op))
                values.push(applyOp(ops.pop(), values.pop(), new BigDecimal(0)));
            else
                values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // Top of 'values' contains the result, return it
        return values.pop();
    }

    /**
     * Evaluates the given mathematical expression with a giving value and returns the result.
     *
     * @param expression The mathematical expression to evaluate.
     * @param x          the value to evaluate with
     * @return The result of the evaluation.
     */
    public static BigDecimal evaluate(String expression, BigDecimal x) {
        // replace x with its given value
        expression = expression.replaceAll("(?<!\\w)x(?!\\w)", String.valueOf(x));
        return evaluate(expression);
    }

    /**
     * Evaluates the given mathematical expression with a giving value and returns the result.
     *
     * @param expression The mathematical expression to evaluate.
     * @param x          the value to evaluate with instead of 'x'
     * @param y          the value to evaluate with instead of 'y'
     * @return The result of the evaluation.
     */
    public static BigDecimal evaluate(String expression, BigDecimal x, BigDecimal y) {
        // replace y with its value
        expression = expression.replaceAll("y", String.valueOf(y));
        return evaluate(expression, x);
    }

    /**
     * Returns true if 'op2' has higher or same precedence as 'op1', otherwise returns false.
     *
     * @param op1 The first operator.
     * @param op2 The second operator.
     * @return True if 'op2' has higher or same precedence as 'op1', false otherwise.
     */
    private static boolean hasPrecedence(String op1, String op2) {
        // Check for parentheses as they have the lowest precedence
        if (Objects.equals(op2, "(") || Objects.equals(op2, ")"))
            return false;
        // Check for precedence rules
        return !isHigherPrecedence(op1, op2);
        //return (!isHigherPrecedence(op1, op2)) && (!isSamePrecedence(op1, op2) || !isLeftAssociative(op1));
    }

    /**
     * Checks if op1 has higher precedence than op2.
     *
     * @param op1 The first operator.
     * @param op2 The second operator.
     * @return True if op1 has higher precedence than op2, false otherwise.
     */
    private static boolean isHigherPrecedence(String op1, String op2) {
        return (isLogSqrtExp(op1) && !isLogSqrtExp(op2)) || isTrigonometric(op1) && !isTrigonometric(op2) ||
                (isExp(op1) && !isLogSqrtExp(op2)) || (isMulDiv(op1) && isAddSub(op2));
    }

    /**
     * Checks if op is an exponential operator (^).
     *
     * @param op The operator.
     * @return True if op is an exponential operator, false otherwise.
     */
    private static boolean isExp(String op) {
        return op.equals("^");
    }

    /**
     * Checks if op is a log, sqrt, or exp operator.
     *
     * @param op The operator.
     * @return True if op is a log, sqrt, or exp operator, false otherwise.
     */
    private static boolean isLogSqrtExp(String op) {
        return op.equals("log") || op.equals("sqrt") || op.equals("exp");
    }

    /**
     * Checks if op is Trigonometric operator.
     *
     * @param op The operator.
     * @return True if op is a Trigonometric operator, false otherwise.
     */
    private static boolean isTrigonometric(String op) {
        return op.equals("sin") || op.equals("cos") || op.equals("tan") ||
                op.equals("asin") || op.equals("acos") || op.equals("atan") ||
                op.equals("sinh") || op.equals("cosh") || op.equals("tanh");
    }

    /**
     * Checks if op is a multiplication or division operator.
     *
     * @param op The operator.
     * @return True if op is a multiplication or division operator, false otherwise.
     */
    private static boolean isMulDiv(String op) {
        return op.equals("*") || op.equals("/");
    }

    /**
     * Checks if op is an addition or subtraction operator.
     *
     * @param op The operator.
     * @return True if op is an addition or subtraction operator, false otherwise.
     */
    private static boolean isAddSub(String op) {
        return op.equals("+") || op.equals("-");
    }

    /**
     * Applies the given operator on operands a and b and returns the result.
     *
     * @param op The operator.
     * @param b  The second operand.
     * @param a  The first operand.
     * @return The result of applying the operator on operands a and b.
     * @throws UnsupportedOperationException If division by zero is encountered.
     */
    private static BigDecimal applyOp(String op, BigDecimal b, BigDecimal a) {
        switch (op) {
            case "+":
                return a.add(b);
            case "-":
                return a.subtract(b);
            case "*":
                return a.multiply(b);
            case "/":
                if (b.compareTo(new BigDecimal(0)) == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a.divide(b, Accuracy.getValue(), RoundingMode.HALF_UP);
            case "log":
                return BigDecimalUtil.ln(b, Accuracy.getValue());
            case "sqrt":
                return BigDecimalUtil.sqrt(b);
            case "exp":
                return BigDecimalUtil.exp(b, Accuracy.getValue());
            case "^":
                return BigDecimalUtil.pow(a, b);
            case "sin":
                return BigDecimalUtil.sine(b);
            case "cos":
                return BigDecimalUtil.cosine(b);
            case "tan":
                return BigDecimalUtil.tangent(b);
            case "asin":
                return BigDecimalUtil.asin(b);
            case "acos":
                return BigDecimalUtil.acos(b);
            case "atan":
                return BigDecimalUtil.atan(b);
            case "sinh":
                return BigDecimalUtil.sinh(b);
            case "cosh":
                return BigDecimalUtil.cosh(b);
            case "tanh":
                return BigDecimalUtil.tanh(b);
        }
        return new BigDecimal(0);
    }

    /**
     * Converts a given string into an ArrayList of Characters.
     * <p>
     * This method takes a String as input and returns an ArrayList of Characters. Each character in the string is
     * added as an individual element to the ArrayList. The order of characters in the ArrayList will be the same as
     * their appearance in the original string.
     *
     * @param inputString The string to be converted into an ArrayList of Characters.
     * @return An ArrayList containing individual characters from the input string.
     * @throws NullPointerException If the inputString is null.
     */
    private static ArrayList<Character> stringToCharArrayList(String inputString) {
        ArrayList<Character> charList = new ArrayList<>();
        for (char c : inputString.toCharArray()) {
            charList.add(c);
        }
        return charList;
    }
}
