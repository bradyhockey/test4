package hw06;

import java.util.Collections;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Creates a record of Expr.
 *
 * @author Tuck
 * @author Brady
 */
record Expr(String text) {
    static ArrayDeque<String> stack;

    /**
     * Evaluates the expression.
     *
     * @return the value of the expression.
     */
    int eval() {
        stack = new ArrayDeque<String>();

        for (var cdpt : text.chars().toArray()) {
            var ch = Character.toString(cdpt);

            if (isSpace(ch) || ch.equals("(")) {
                continue;
            }

            if (isOperator(ch)) {
                stack.push(ch);
                continue;
            }

            if (isDigits(ch)) {
                if (isDigits(stack.peek())) {
                    String num = stack.pop();
                    var xx = num + ch;
                    stack.push(xx);
                } else {
                    stack.push(ch);
                }
                continue;
            }

            if (ch.equals(")")) {
                var num1 = Integer.parseInt(stack.pop());
                var op = stack.pop();
                var num2 = Integer.parseInt(stack.pop());

                if (op.equals("+")) {
                    stack.push(("" + (num2 + num1)));
                }

                else if (op.equals("-")) {
                    stack.push("" + (num2 - num1));
                }

                else if (op.equals("/")) {
                    stack.push("" + (num2 / num1));
                }

                else if (op.equals("*")) {
                    stack.push("" + (num2 * num1));
                }
                continue;
            }
        }

        try {
            return Integer.parseInt(stack.pop());
        } catch (NumberFormatException ee) {
            dumpStack();
            throw new RuntimeException("expected number: " + ee.toString());
        }
    }

    /**
     * Checks if it's a space.
     *
     * @param xx the char.
     * @return true or false.
     */
    static boolean isSpace(String xx) {
        return xx != null && Pattern.matches("^\\s+$", xx);
    }

    /**
     * Checks if it's a digit.
     *
     * @param xx the char.
     * @return true or false.
     */
    static boolean isDigits(String xx) {
        return xx != null && Pattern.matches("^[0-9]+$", xx);
    }

    /**
     * Checks if it's a n operation.
     *
     * @param xx the char.
     * @return true or false.
     */
    static boolean isOperator(String xx) {
        String ops = "+-*/";
        return xx != null && ops.contains(xx);
    }

    /**
     * Applies the operations.
     *
     * @param a1 num 1.
     * @param op the operations.
     * @param a2 num 2.
     * @return the
     */
    static String applyOp(String a1, String op, String a2) {
        // System.out.printf("applyOp(%s %s %s)\n", a1, op, a2);
        try {
            var xx = Integer.parseInt(a1);
            var yy = Integer.parseInt(a2);

            if (op.equals("+")) {
                return Integer.toString(xx + yy);
            }

            if (op.equals("-")) {
                return Integer.toString(xx - yy);
            }

            if (op.equals("*")) {
                return Integer.toString(xx * yy);
            }

            if (op.equals("/")) {
                return Integer.toString(xx / yy);
            }

            dumpStack();
            throw new RuntimeException("bad operator: " + op);
        } catch (NumberFormatException ee) {
            dumpStack();
            throw new RuntimeException("bad number: " + ee.toString());
        }
    }

    /**
     * Checks if it's a space.
     *
     * @param xx the char.
     * @return true or false.
     */
    static void dumpStack() {
        var xs = new ArrayList(stack);
        Collections.reverse(xs);
        for (var xx : xs) {
            System.out.println(xx);
        }
    }
}
