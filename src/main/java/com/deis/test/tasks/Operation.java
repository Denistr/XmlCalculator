package com.deis.test.tasks;

/**
 * Created by denis on 28.08.18.
 */
public class Operation {

    private Operation firstOperation;
    private Operation secondOperation;
    private OperationType operationType;
    private Double value;

    public Operation getFirstOperation() {
        return firstOperation;
    }

    public void setFirstOperation(Operation firstOperation) {
        this.firstOperation = firstOperation;
    }

    public Operation getSecondOperation() {
        return secondOperation;
    }

    public void setSecondOperation(Operation secondOperation) {
        this.secondOperation = secondOperation;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public enum OperationType {
        SUB, DIV, SUM, MUL
    }
}
