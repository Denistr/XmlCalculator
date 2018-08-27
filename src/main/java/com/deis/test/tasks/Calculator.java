package com.deis.test.tasks;

/**
 * Created by denis on 27.08.18.
 */
public class Calculator {
    private Double firstVal;
    private Double secondVal;
    private Operation operation;


    Calculator(Double firstVal, Double secondVal, Operation operation) {
        this.firstVal = firstVal;
        this.secondVal = secondVal;
        this.operation = operation;
    }

    public Double getFirstVal() {
        return firstVal;
    }

    public void setFirstVal(Double firstVal) {
        this.firstVal = firstVal;
    }

    public Double getSecondVal() {
        return secondVal;
    }

    public void setSecondVal(Double secondVal) {
        this.secondVal = secondVal;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Double calculate() throws Exception {
        switch (operation) {
            case SUB:
                return firstVal - secondVal;
            case MUL:
                return firstVal * secondVal;
            case DIV:
                if (secondVal == null) {
                    throw new Exception("Division to null");
                }
                return firstVal / secondVal;
            case SUM:
                return firstVal + secondVal;
            default:
                throw new Exception("Unsupported operation");
        }

    }

    public enum Operation {
        SUB, DIV, SUM, MUL
    }
}
