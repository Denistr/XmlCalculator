package com.deis.test.tasks;

/**
 * Created by denis on 27.08.18.
 */
public class Calculator {

    public double calculate(Operation operation) throws Exception {

        if (operation.getOperationType() == null) {
            return operation.getValue().doubleValue();
        } else {
            double operationResultFirst = calculate(operation.getFirstOperation());
            double operationResultSecond = calculate(operation.getSecondOperation());
            return calculateValue(operationResultFirst, operationResultSecond, operation.getOperationType());
        }
    }

    private double calculateValue(double firstVal, double secondVal, Operation.OperationType operation) throws Exception {
        switch (operation) {
            case SUB:
                return firstVal - secondVal;
            case MUL:
                return firstVal * secondVal;
            case DIV:
                if (firstVal == 0) {
                    throw new Exception("Division to null");
                }
                return firstVal / secondVal;
            case SUM:
                return firstVal + secondVal;
            default:
                throw new Exception("Unsupported operation");
        }
    }
}
