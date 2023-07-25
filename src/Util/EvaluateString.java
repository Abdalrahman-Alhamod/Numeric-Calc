package Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<BigDecimal> values = new Stack<>();
        // Stack for Operators: 'ops'
        Stack<String> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            String preOp = "1";
            if (i > 0)
                preOp = Character.toString(tokens[i - 1]);

            if ((i == 0 && tokens[i] == '-') && Character.isDigit(tokens[i + 1]) ||
                    i > 0 && tokens[i] == '-' && (preOp.equals("(") || isExp(preOp) ||
                            isTrigonometric(preOp) || isLogSqrtExp(preOp) ||
                            isMulDiv(preOp) || isAddSub(preOp)) && Character.isDigit(tokens[i + 1])) {
                i++;
                StringBuilder sb = new StringBuilder();

                // Extract the whole number or decimal part
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.'))
                    sb.append(tokens[i++]);
                values.push(new BigDecimal(sb.toString()).multiply(new BigDecimal(-1)));
                // Right now the 'i' points to the character next to the number,
                // Since the for loop also increases 'i', we need to decrease the value of 'i' by 1 to correct the offset.
                i--;
            }
            // Current token is a number, push it to stack for numbers
            else if (Character.isDigit(tokens[i]) || tokens[i] == '.') {
                StringBuilder sb = new StringBuilder();

                // Extract the whole number or decimal part
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.'))
                    sb.append(tokens[i++]);
                values.push(new BigDecimal(sb.toString()));

                // Right now the 'i' points to the character next to the number,
                // Since the for loop also increases 'i', we need to decrease the value of 'i' by 1 to correct the offset.
                i--;
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(String.valueOf(tokens[i]));

                // Closing brace encountered, solve the entire brace
            else if (tokens[i] == ')') {
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
            else if (tokens[i] == 'l' && i + 2 < tokens.length && tokens[i + 1] == 'o' && tokens[i + 2] == 'g') {
                ops.push("log");
                i += 2;
            }
            // Handle the sqrt operator
            else if (tokens[i] == 's' && i + 3 < tokens.length && tokens[i + 1] == 'q' && tokens[i + 2] == 'r' && tokens[i + 3] == 't') {
                ops.push("sqrt");
                i += 3;
            }
            // Handle the exp operator
            else if (tokens[i] == 'e' && i + 2 < tokens.length && tokens[i + 1] == 'x' && tokens[i + 2] == 'p') {
                ops.push("exp");
                i += 2;
            }
            // Handle the sin operator
            else if (tokens[i] == 's' && i + 2 < tokens.length && tokens[i + 1] == 'i' && tokens[i + 2] == 'n') {
                ops.push("sin");
                i += 2;
            }
            // Handle the cos operator
            else if (tokens[i] == 'c' && i + 2 < tokens.length && tokens[i + 1] == 'o' && tokens[i + 2] == 's') {
                ops.push("cos");
                i += 2;
            }
            // Handle the tan operator
            else if (tokens[i] == 't' && i + 2 < tokens.length && tokens[i + 1] == 'a' && tokens[i + 2] == 'n') {
                ops.push("tan");
                i += 2;
            }
            // Handle the asin operator
            else if (tokens[i] == 'a' && i + 3 < tokens.length && tokens[i + 1] == 's' && tokens[i + 2] == 'i' && tokens[i + 3] == 'n') {
                ops.push("asin");
                i += 3;
            }
            // Handle the acos operator
            else if (tokens[i] == 'a' && i + 3 < tokens.length && tokens[i + 1] == 'c' && tokens[i + 2] == 'o' && tokens[i + 3] == 's') {
                ops.push("acos");
                i += 3;
            }
            // Handle the atan operator
            else if (tokens[i] == 'a' && i + 3 < tokens.length && tokens[i + 1] == 't' && tokens[i + 2] == 'a' && tokens[i + 3] == 'n') {
                ops.push("atan");
                i += 3;
            }
            // Current token is an operator
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
                // While the top of 'ops' has the same or greater precedence to the current token, which is an operator.
                // Apply the operator on top of 'ops' to the top two elements in the values stack
                while (!ops.empty() && hasPrecedence(String.valueOf(tokens[i]), ops.peek())) {
                    String op = ops.peek();
                    if (isLogSqrtExp(op) || isTrigonometric(op))
                        values.push(applyOp(ops.pop(), values.pop(), new BigDecimal(0)));
                    else
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                // Push the current token to 'ops'
                ops.push(String.valueOf(tokens[i]));
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
     * Checks if op1 and op2 have the same precedence.
     *
     * @param op1 The first operator.
     * @param op2 The second operator.
     * @return True if op1 and op2 have the same precedence, false otherwise.
     */
    private static boolean isSamePrecedence(String op1, String op2) {
        return (isMulDiv(op1) && isMulDiv(op2)) || (isAddSub(op1) && isAddSub(op2)) || (isExp(op1) && isExp(op2));
    }

    /**
     * Checks if op is left associative.
     *
     * @param op The operator.
     * @return True if op is left associative, false otherwise.
     */
    private static boolean isLeftAssociative(String op) {
        return isAddSub(op) || isMulDiv(op) || isExp(op);
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
        }
        return new BigDecimal(0);
    }
}
