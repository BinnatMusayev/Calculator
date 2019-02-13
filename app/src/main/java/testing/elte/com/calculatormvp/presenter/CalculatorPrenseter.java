package testing.elte.com.calculatormvp.presenter;

import java.util.ArrayList;

import testing.elte.com.calculatormvp.model.Calculator;

public class CalculatorPrenseter {

    Calculator calculator;
    MainView mainView ;

    public CalculatorPrenseter(MainView mainView){
        this.calculator= new Calculator();
        this.mainView = mainView;
    }

    public void numClicked(int n){
        if (!calculator.getCalculationText().equals("0")
                && !calculator.isErrorOccured()
                && !calculator.getCalculationText().equalsIgnoreCase("Infinity")
                && !calculator.getCalculationText().equalsIgnoreCase("Error")){


            if(lastNum().equals("0") && n == 0 ){
                //avaiding adding 0 continuosly
            }else{
                if (calculator.isNewNumber()){
                    calculator.setCalculationText(String.valueOf(n));
                    calculator.setNewNumber(false);
                }else {
                    calculator.setCalculationText(calculator.getCalculationText() + n);
                }
            }
        } else {
                calculator.setCalculationText(String.valueOf(n));
        }

        calculator.setErrorOccured(false);
        mainView.setText(calculator.getCalculationText());

    }

    public void dotClicked(){
        if (!lastNumContainsDot() ){
                //everything okay
                calculator.setCalculationText(calculator.getCalculationText() + ".");
                mainView.setText(calculator.getCalculationText());
        }
    }

    public void operatorClicked(String operator){
        if (calculator.isOperatorEntered()){
            calculateWithoutOperation();
            calculator.setFirstnum(calculator.getResult());
            mainView.setText(String.valueOf(calculator.getResult()));

        }else {
            calculator.setFirstnum(Float.valueOf(calculator.getCalculationText()));
        }
        calculator.setOperation(operator.charAt(0));
        calculator.setNewNumber(true);
        calculator.setOperatorEntered(true);

    }

    public void clear(){
        calculator.reinstantiateEverything();
        mainView.clearTextView();
    }

    public void calculate(){
        calculator.setSecondnum(Float.valueOf(calculator.getCalculationText()));
        calculator.calculate();
        if (calculator.isErrorOccured()){
            mainView.setText("Error");
            calculator.reinstantiateEverything();
        }else{
            mainView.setText(String.valueOf(calculator.getResult()));
            calculator.setFirstnum(calculator.getResult());
        }
        calculator.setCalculationText(String.valueOf(calculator.getResult()));
        calculator.setOperatorEntered(false);
    }

    public void calculateWithoutOperation(){
        calculator.setSecondnum(Float.valueOf(calculator.getCalculationText()));
        calculator.calculate();
        if (calculator.isErrorOccured()){
            mainView.setText("Error");
            calculator.reinstantiateEverything();
        }else{
            mainView.setText(String.valueOf(calculator.getResult()));
            calculator.setFirstnum(calculator.getResult());
        }
        calculator.setCalculationText(String.valueOf(calculator.getResult()));
    }

    public boolean lastNumContainsDot(){
        return lastNum().contains(".");
    }

    public String lastNum(){
        String wholeText = calculator.getCalculationText();
        StringBuilder lastNumBuilder = new StringBuilder();
        for(int i=wholeText.length()-1; i>=0; i--){
            char c = wholeText.charAt(i);
            if(c != '+' && c != '-' &&  c != '*' && c != '/' ){
                lastNumBuilder.insert(0, c);
            }else{
                return lastNumBuilder.toString();
            }
        }
        return lastNumBuilder.toString();
    }

    //to get the last number of given string
    public String lastNum(String str){
        StringBuilder lastNumBuilder = new StringBuilder();
        for(int i=str.length()-1; i>=0; i--){
            char c = str.charAt(i);
            if(c != '+' && c != '-' &&  c != '*' && c != '/' ){
                lastNumBuilder.insert(0, c);
            }else{
                return lastNumBuilder.toString();
            }
        }
        return lastNumBuilder.toString();
    }

    //to get the first number of given string
    public String firstNum(String str){
        StringBuilder lastNumBuilder = new StringBuilder();
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(c != '+' && c != '-' &&  c != '*' && c != '/' ){
                lastNumBuilder.insert(0, c);
            }else{
                return lastNumBuilder.toString();
            }
        }
        return lastNumBuilder.toString();
    }

    public void deleteLast(){
        String str = calculator.getCalculationText();
        if (str.length() == 1){
            calculator.setCalculationText("0");
        }else{
            calculator.setCalculationText(str.substring(0, str.length()-1));
        }

        mainView.setText(calculator.getCalculationText());
    }




    //interface for view
    public interface MainView{
        void clearTextView();
        void setText(String text);
    }

}
