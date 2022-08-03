package com.curry.function.calculate.calculator;

import java.util.Stack;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-03 12:03
 * @description:
 * 计算器类，对一个算式进行科学计算,返回一个double的值
 * 对普通计算器和科学计算器都进行考虑,让这一个类可以用于两种计算器
 * 两种乘法,考虑了不同情况下,即省略与不省略的情况下不同的优先级,显然省略的时候优先级会更高,在计算根号的时候体现的更加明显
 **/
public class Calculator {
    private Stack<Character> operators;
    private Stack<Double> numbers;

    private String mFormula;

    public Calculator(){
        mFormula = "";
        operators = new Stack<>();
        numbers = new Stack<>();
    }

    /**
     * 根据operator计算stack中前两个数字,并且将计算结果压栈
     * @param stack
     * @param operator
     * @return
     */
    private boolean calculate(Stack<Double> stack, char operator){
        double num1, num2;
        double num3 = 0;
        switch (operator){
            //得先判断能不能做此种运算运算
            case'+':
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.pop();
                num3 = num1 + num2;
                break;
            case '-':
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.pop();
                num3 = num1 - num2;
                break;
            case 'x':
            case 'X':
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.pop();
                num3 = num1 * num2;
                break;
            case '÷':
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.pop();
                num3 = num1 / num2;
                break;
            case '%' :
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.peek();
                if((int)num1 == num1 && (int)num2 == num2){
                    stack.pop();
                    num3 = num1 % num2;
                }else {
                    stack.push(num2);
                    return false;
                }
                break;
            case 's':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.sin(stack.pop());
                break;
            case 'S':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.asin(stack.pop());
                break;
            case 'c':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.cos(stack.pop());
                break;
            case 'C':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.acos(stack.pop());
                break;
            case 't':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.tan(stack.pop());
                break;
            case 'T':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.atan(stack.pop());
                break;
            case '^':
                if(stack.size() < 2){
                    return false;
                }
                num2 = stack.pop();
                num1 = stack.pop();
                num3 = Math.pow(num1, num2);
                break;
            case 'L':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.log10(stack.pop());
                break;
            case 'l':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.log(stack.pop());
                break;
            case '(':
            case ')':
                return false;
            case '√':
                if(stack.size() < 1){
                    return false;
                }
                num3 = Math.pow(stack.pop(), 0.5);
                break;
            case '!':
                if(stack.size() < 1){
                    return false;
                }
                num2 = stack.peek();
                if((int)num2 == num2 && num2 >= 0){
                    stack.pop();
                    num3 = 1;
                    for(int i = 1;i <= num2; i++){
                        num3*=i;
                    }
                }else {
                    stack.push(num2);
                    return false;
                }
                break;
        }
        stack.push(num3);
        return true;
    }

    /**
     * 计算中缀表达式
     * @param formula
     * @return
     */
    public double cal(String formula){
        mFormula = formula;
        numbers.clear();
        operators.clear();

        String num = "";
        char ch;

        for(int i = 0; i < mFormula.length(); i++){
            ch  =mFormula.charAt(i);
            if(Character.isDigit(ch) || ch == '.' || ch == 'p' || ch == 'e'){
                //数字
                if(ch == 'p' ){
                    num = String.valueOf(num.equals("") ? Math.PI : Double.parseDouble(num) * Math.PI);
                }else if(ch == 'e'){
                    num = String.valueOf(num.equals("") ? Math.E : Double.parseDouble(num) * Math.E);
                }else {
                    num += ch;
                }
                //将这个数字压进数字栈
                if(i == mFormula.length() - 1 || isOperator(mFormula.charAt(i+1))){
                    //System.out.println(Double.parseDouble(num));
                    //如果单独输入了一个。
                    if(num.equals(".")){
                        return Double.MIN_VALUE;
                    }
                    numbers.push(Double.parseDouble(num));
                    num = "";
                }
            }
            else if(isOperator(ch)){
                //运算符号,注意负号的特殊性,可能让这个负号称为数字的一部分所以放在最开始
                if(ch == '-'){
                    if(i == 0 || mFormula.charAt(i -1) == '^' ||
                            mFormula.charAt(i-1) == '(' ||
                            mFormula.charAt(i - 1) == '√'){
                        if(mFormula.length() > i && mFormula.charAt(i + 1) == '('){
                            //如果后面是一个左括号
                            numbers.push(Double.parseDouble("0"));
                            operators.push('-');
                            continue;
                        }else if(mFormula.length() > i && Character.isDigit(mFormula.charAt(i + 1))) {
                            //如果后面是一个数字,判断下一个运算符的优先级来确定是否单目
                            boolean flag = false;
                            for(int j = i + 1; j < mFormula.length() ;j++){
                                if(!Character.isDigit(mFormula.charAt(j)) && mFormula.charAt(j) != '.'){
                                    if(getPriority(mFormula.charAt(j)) > getPriority('-')){
                                        flag = true;
                                    }
                                    break;
                                }
                            }
                            if(flag){
                                //后面紧跟优先级更高的运算符
                                numbers.push(Double.parseDouble("0"));
                                operators.push('-');
                                continue;
                            }else {
                                num += '-';
                                continue;
                            }
                        }
                    }
                }
                //阶乘由于其独特的左结合性，需要直接计算这个时候的数字栈顶,否则程序出现逻辑错误
                else if(ch == '!'){
                    if(i >0 && isOperator(mFormula.charAt(i - 1))){
                        return Double.MIN_VALUE;//如果阶乘前面直接是一个运算符
                    }else {
                        if (numbers.isEmpty()) {
                            return Double.MIN_VALUE;//如果这个时候数字栈没有数字,表示无法计算
                        } else {
                            if (!calculate(numbers, ch)) return Double.MIN_VALUE;//可以计算
                        }
                    }
                    continue;
                }
                //当符号栈为空的时候直接压栈,否则可能会出现计算
                if(operators.isEmpty()){
                    operators.push(ch);
                }else{
                    //如果这是一个),计算出它到(之间的所有的符号
                    if(ch == ')'){
                        if(i == 0){
                            return Double.MIN_VALUE;
                        }
                        while(!operators.isEmpty() && operators.peek() != '('){
                            if(!calculate(numbers, operators.pop())){
                                return Double.MIN_VALUE;
                            }
                        }
                        if(operators.isEmpty()) {
                            return Double.MIN_VALUE;
                        }else{
                            operators.pop();//弹出'('
                        }
                        continue;
                    }else if(getPriority(operators.peek()) >= getPriority(ch) && operators.peek() != '('){
                        //当前符号栈的栈顶优先级更高,排除'('这个最高优先级的符号,防止出现对(进行计算(这是无意义的)
                        while(!operators.isEmpty() && getPriority(operators.peek()) >= getPriority(ch) && operators.peek() != '(') {
                            if (!calculate(numbers, operators.pop())) {
                                return Double.MIN_VALUE;
                            }
                        }
                        //计算完毕将这个优先级次级的压栈
                        operators.push(ch);
                    }else{
                        //如果这个运算符的优先级更高,不计算
                        operators.push(ch);
                    }
                }
            }else{
                //不是数字也不是运算符号,算式错误
                return Double.MIN_VALUE;
            }
        }
        while(!operators.isEmpty()){
            //如果需要两个操作数但是没有两个数字了
            if(numbers.size() < 2 && requireTwoOp(operators.peek())){
                return Double.MIN_VALUE;
            }
            if(!calculate(numbers, operators.pop())){
                return Double.MIN_VALUE;
            }
        }
        if(numbers.size() == 1) {
            return numbers.pop();
        }else{
            return Double.MIN_VALUE;
        }
    }

    /**
     * 将中缀表达式转化为后缀表达式
     * @param formula
     * @return
     */
    public String toPostfix(String formula){
        mFormula = formula;
        numbers.clear();
        operators.clear();

        String num = "";
        String postfix = "";
        char ch;

        for(int i = 0; i < mFormula.length(); i++){
            ch  =mFormula.charAt(i);
            if(Character.isDigit(ch) || ch == '.' || ch == 'p' || ch == 'e'){
                //数字
                if(ch == 'p' ){
                    num = String.valueOf(num.equals("") ? Math.PI : Double.parseDouble(num) * Math.PI);
                }else if(ch == 'e'){
                    num = String.valueOf(num.equals("") ? Math.E : Double.parseDouble(num) * Math.E);
                }else {
                    num += ch;
                }
                //数字就直接加入表达式
                if(i == mFormula.length() - 1 || isOperator(mFormula.charAt(i+1))){
                    //如果单独输入了一个。
                    if (num.equals(".")){
                        return null;
                    }
                    postfix =postfix + num + " ";
                    num = "";
                }
            }
            else if(isOperator(ch)){
                //运算符号,注意负号的特殊性,可能让这个负号称为数字的一部分所以放在最开始
                if(ch == '-'){
                    if(i == 0 || mFormula.charAt(i -1) == '^' ||
                            mFormula.charAt(i-1) == '(' ||
                            mFormula.charAt(i - 1) == '√' ){
                        if(mFormula.length() > i && mFormula.charAt(i + 1) == '('){
                            //如果符号后面是一个左括号,这时负号不是单目
                            postfix = postfix + "0 ";
                            operators.push('-');
                            continue;
                        }else if(mFormula.length() > i && Character.isDigit(mFormula.charAt(i + 1))) {
                            //如果后面是一个数字,情况变为判断下一个运算符是否优先级更高,是则也不算是单目
                            boolean flag = false;
                            for (int j = i + 1; j < mFormula.length(); j++) {
                                if (!Character.isDigit(mFormula.charAt(j)) && mFormula.charAt(j) != '.') {
                                    if (getPriority(mFormula.charAt(j)) > getPriority('-')) {
                                        flag = true;
                                    }
                                    break;
                                }
                            }
                            if (flag) {
                                //后面紧跟优先级更高的运算符
                                postfix = postfix + "0 ";
                                operators.push('-');
                                continue;
                            } else {
                                num += '-';
                                continue;
                            }
                        }
                    }
                }
                //阶乘由于其特殊的左结合性，需要把这个符号直接加入到后缀表达式中，否则出现逻辑错误
                if(ch == '!'){
                    if(i >0 && isOperator(mFormula.charAt(i - 1))){
                        return null;//如果阶乘前面直接是一个运算符
                    }else {
                        if (postfix.equals("")) {
                            return null;//如果这个时候表达式没有数字,表示无法计算
                        } else {
                            postfix = postfix + ch + " ";
                        }
                    }
                    continue;
                }
                //当符号栈为空的时候直接压栈
                if(operators.isEmpty()){
                    operators.push(ch);
                }else{
                    //如果这是一个),它到(之间的所有的符号都添加到表达式中
                    if(ch == ')'){
                        if(i == 0){
                            return null;
                        }
                        while(!operators.isEmpty() && operators.peek() != '('){
                            postfix = postfix + operators.pop() + " ";
                        }
                        if(operators.isEmpty()) {
                            return null;
                        }else{
                            operators.pop();//弹出'('
                        }
                        continue;
                    }else if(getPriority(operators.peek()) >= getPriority(ch) && operators.peek() != '('){
                        //当前符号栈的栈顶优先级更高,排除'('这个最高优先级的符号,防止出现对(进行计算(这是无意义的)
                        while(!operators.isEmpty() && getPriority(operators.peek()) >= getPriority(ch) && operators.peek() != '(') {
                            postfix = postfix + operators.pop() + " ";
                            //计算完毕将这个优先级次级的运算符压栈
                        }
                        operators.push(ch);
                    }else{
                        //如果这个运算符的优先级更高,不计算
                        operators.push(ch);
                    }
                }
            }else{
                //不是数字也不是运算符号,算式错误
                return null;
            }
        }
        while(!operators.isEmpty()){
            postfix = postfix + operators.pop() + " ";
        }
        return postfix;
    }

    /**
     * 对后缀表达式进行计算，这个应用中并没有用到，简单测试后，确认可以正常运行
     * @param formula
     * @return
     */
    public double calByPostfix(String formula){
        numbers.clear();
        operators.clear();

        for(String each: formula.split(" ")){
            if(each.length() == 1 && isOperator(each.charAt(0))){
                //如果它是一个运算符,直接计算
                if(!calculate(numbers, each.charAt(0))){
                    return Double.MIN_VALUE;
                }
            }else{
                //否则对数字直接俄压栈
                numbers.push(Double.parseDouble(each));
            }
        }
        if(numbers.size() == 1){
            return numbers.pop();
        }else{
            return Double.MIN_VALUE;
        }
    }


    /**
     * 判断是否是一个操作符
     * @param ch
     * @return
     */
    private boolean isOperator(char ch){
        if(ch == '+' || ch == '-' || ch == 'x' || ch == '÷' || ch == '%' || ch == '(' || ch == ')' ||
                ch == 's' || ch == 'S' || ch == 'c' || ch == 'C' || ch == 't' || ch == 'T' ||
                ch == 'l' || ch == 'L' || ch == '^' || ch == '!' || ch == '√' || ch == 'X'){
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要两个操作数
     * @param ch
     * @return
     */
    private boolean requireTwoOp(char ch){
        if(ch == '+' || ch == '-' || ch == 'x' || ch == '÷' || ch == '%' || ch == '^' || ch == 'X'){
            return true;
        }
        return false;
    }

    /**
     * 运算符的优先级
     * @param operator
     * @return
     */
    private int getPriority(char operator){
        switch (operator){
            case ')':
                return 1;
            case '+':
            case '-':
                return 2;
            case 'x':
            case '÷':
            case '%':
                return 3;
            case 's':
            case 'S':
            case 'c':
            case 'C':
            case 't':
            case 'T':
                return 4;
            case 'l':
            case 'L':
                return 5;
            case 'X':
                return 6;
            case '^':
            case '√':
                return 7;
            case '!':
                return 8;
            case '(':
                return 9;
        }
        return -1;
    }
}
