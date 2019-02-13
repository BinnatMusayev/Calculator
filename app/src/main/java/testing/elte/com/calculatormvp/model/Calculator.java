package testing.elte.com.calculatormvp.model;

public class Calculator {

    private float firstnum, secondnum, result;
    private boolean errorOccured, operatorEntered, newNumber;
    private char operation;
    private String calculationText;

    public Calculator(){
        this.result = 0;
        this.calculationText="0";
    }

    public void calculate(){
        switch (operation){
            case '*':
                result = firstnum * secondnum;
                break;
            case '/':
                if (secondnum == 0 || secondnum == 0.0 ){
                    errorOccured = true;
                    result = 0;
                }else {
                    result = firstnum / secondnum;
                }
                break;
            case '+':
                result = firstnum + secondnum;
                break;
            case '-':
                result = firstnum - secondnum;
                break;
            default:
                errorOccured = true;
        }
    }

    public float getFirstnum() {
        return firstnum;
    }

    public void setFirstnum(float firstnum) {
        this.firstnum = firstnum;
    }

    public float getSecondnum() {
        return secondnum;
    }

    public void setSecondnum(float secondnum) {
        this.secondnum = secondnum;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public String getCalculationText() {
        return calculationText;
    }

    public void setErrorOccured(boolean errorOccured) {
        this.errorOccured = errorOccured;
    }

    public boolean isErrorOccured() {
        return errorOccured;
    }

    public void setCalculationText(String calculationText) {
        this.calculationText = calculationText;
    }

    public boolean isOperatorEntered() {
        return operatorEntered;
    }

    public void setOperatorEntered(boolean operatorEntered) {
        this.operatorEntered = operatorEntered;
    }

    public boolean isNewNumber() {
        return newNumber;
    }

    public void setNewNumber(boolean newNumber) {
        this.newNumber = newNumber;
    }
}
