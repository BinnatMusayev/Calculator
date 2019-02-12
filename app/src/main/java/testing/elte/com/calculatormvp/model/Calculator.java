package testing.elte.com.calculatormvp.model;

public class Calculator {

    private int result;
    private String calculationText;

    public Calculator(){
        this.result = 0;
        this.calculationText="0";
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCalculationText() {
        return calculationText;
    }

    public void setCalculationText(String calculationText) {
        this.calculationText = calculationText;
    }
}
