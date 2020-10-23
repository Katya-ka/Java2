package com.company;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static Boolean delim(char c) {
        return c == ' ';
    }

    public static Boolean is_op(char c) {
        return c=='+' || c=='-' || c=='*' || c=='/' || c=='%';
    }

    static int priority(char op) {
        return
                op == '+' || op == '-' ? 1 :
                        op == '*' || op == '/' || op == '%' ? 2 :
                                -1;
    }

    static void process_op(Stack<Integer> st, char op) {
        int r = st.peek();  st.pop();
        int l = st.peek();  st.pop();
        switch (op) {
            case '+' -> st.add(l + r);
            case '-' -> st.add(l - r);
            case '*' -> st.add(l * r);
            case '/' -> st.add(l / r);
            case '%' -> st.add(l % r);
        }
    }

    static int calc(String s) {
        Stack<Integer> st = new Stack<>();
        Stack<Character> op = new Stack<>();
        for (int i=0; i < s.length(); ++i)
            if (!delim(s.charAt(i)))
                if (s.charAt(i) == '(')
                    op.add ('(');
                else if (s.charAt(i) == ')') {
                    while (op.peek() != '(') {
                        process_op(st, op.peek());
                        op.pop();
                    }
                    op.pop();
                }
                else if (is_op (s.charAt(i))) {
                    char curop = s.charAt(i);
                    while (!op.empty() && priority(op.peek()) >= priority(s.charAt(i))) {
                        process_op(st, op.peek());
                        op.pop();
                    }
                    op.add(curop);
                }
                else {
                    StringBuilder operand = new StringBuilder();
                    while (i < s.length() && Character.isDigit(s.charAt(i)))
                        operand.append(s.charAt(i++));
                    --i;
                    if (Character.isDigit(operand.charAt(0)))
                        st.add(Integer.parseInt(operand.toString()));
                }
        while (!op.empty()) {
            process_op(st, op.peek());
            op.pop();
        }
        return st.peek();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(calc(sc.nextLine()));
    }
}