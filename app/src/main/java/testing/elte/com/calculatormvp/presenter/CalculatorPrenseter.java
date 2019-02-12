package testing.elte.com.calculatormvp.presenter;

import java.util.ArrayList;

import testing.elte.com.calculatormvp.model.Calculator;

public class CalculatorPrenseter {

    Calculator calculator;
    MainView mainView ;
    private ArrayList<Character> operators;

    public CalculatorPrenseter(MainView mainView){
        this.calculator= new Calculator();
        this.mainView = mainView;

        operators = new ArrayList<>();
    }

    public void numClicked(int n){
        if (!calculator.getCalculationText().equals("0")
                && !calculator.getCalculationText().equalsIgnoreCase("Infinity")
                && !calculator.getCalculationText().equalsIgnoreCase("Error")){


            if( lastNum().equals("0") && n == 0 && containsOperator()){
                //avaiding adding 0 continuosly
            }else{
                calculator.setCalculationText(calculator.getCalculationText() + n);
            }
        } else {
                calculator.setCalculationText(String.valueOf(n));
        }

        mainView.setText(calculator.getCalculationText());

    }

    public void dotClicked(){
        if (!lastNumContainsDot() && !isLastCharOperator()){
                //everything okay
                calculator.setCalculationText(calculator.getCalculationText() + ".");
                mainView.setText(calculator.getCalculationText());
        }
    }

    public void operatorClicked(String operator){
        if (isLastCharOperator() ){
            calculator.setCalculationText(
                    calculator.getCalculationText().substring(0, calculator.getCalculationText().length()-1) + operator);
        }else {
            calculator.setCalculationText(calculator.getCalculationText() + operator);
        }
        mainView.setText(calculator.getCalculationText());
    }

    public void clear(){
        calculator.setCalculationText("0");
        mainView.clearTextView();
    }

    public void calculate(){
        if (!isLastCharOperator() || calculator.getCalculationText().endsWith(".")){
//            mainView.setText(lastNum());
            generateOperatorsArray();
//            try {
                eliminateHigherOrder();
                finalizeCalculation();
//            }catch (Exception e){
//                mainView.setText("Error");
//                e.printStackTrace();
//            }
//            mainView.setText(operators.toString());
        }
    }

    public boolean isLastCharOperator(){
        char lastChar = calculator.getCalculationText().charAt(calculator.getCalculationText().length()-1);
        return lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '.' ;
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

    public boolean containsOperator(){
        String str = calculator.getCalculationText();
//        return str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/");
        return operators.size()>0;
    }

    public boolean containsHigherOrderOperator(){
        String str = calculator.getCalculationText();
        return str.contains("*") || str.contains("/");
    }

    public boolean containsMultiplication(){
        String str = calculator.getCalculationText();
        return str.contains("*") ;
    }

    public boolean containsDivision(){
        String str = calculator.getCalculationText();
        return str.contains("/");
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

    public void generateOperatorsArray(){
        operators.clear();
        for(int i=0; i<calculator.getCalculationText().length(); i++){
            char c = calculator.getCalculationText().charAt(i);
            if(c == '+' || c == '-' ||  c == '*' || c == '/' ){
                operators.add(c);
            }
        }
    }

    private void eliminateHigherOrder() {//throws Exception{
        while(containsHigherOrderOperator()){
            float  result;
            Number firstNum, secondNum;
            String calcText = calculator.getCalculationText();
            char firstHigherOrderOperation = getFirstHigherOrder();
            String firstNumString;
            if(!calcText.startsWith("-")) {
                firstNumString = lastNum(calcText.substring(0, calcText.indexOf(String.valueOf(firstHigherOrderOperation))));
            }else{
                if (operators.size() <= 2) {
                    firstNumString = "-" + lastNum(calcText.substring(1, calcText.indexOf(String.valueOf(firstHigherOrderOperation))));
                }else{
                    firstNumString = lastNum(calcText.substring(0, calcText.indexOf(String.valueOf(firstHigherOrderOperation))));
                }
            }

            if(firstNumString.contains(".")){
                firstNum = Float.valueOf(firstNumString);
            }else{
                firstNum = Integer.valueOf(firstNumString);
            }

            String secondNumString = firstNum(calcText.substring(calcText.indexOf(String.valueOf(firstHigherOrderOperation))+1));
            if(secondNumString.contains(".")){
                secondNum = Float.valueOf(secondNumString);
            }else{
                secondNum = Integer.valueOf(secondNumString);
            }


            if(firstHigherOrderOperation == '*'){
                result = Float.parseFloat(String.valueOf(firstNum))*Float.parseFloat(String.valueOf(secondNum));
            }else{
                result = Float.parseFloat(String.valueOf(firstNum))/Float.parseFloat(String.valueOf(secondNum));
            }

            String oldSubString = String.valueOf(firstNum)+ String.valueOf(firstHigherOrderOperation) + String.valueOf(secondNum);
            calculator.setCalculationText(calculator.getCalculationText().replace(oldSubString, String.valueOf(result)) );
            if (!containsOperator()) {
                mainView.setText(calculator.getCalculationText());
            }
            operators.remove(operators.indexOf(getFirstHigherOrder()));
            if(calcText.startsWith("-") && operators.size() <=2) {
                operators.remove(0);
            }
            mainView.setText(String.valueOf(result));

        }

        //for cases like 2*5+2*5
        while (operators.contains('*') || operators.contains('/')){
            operators.remove(operators.indexOf(getFirstHigherOrder()));
        }
    }

    public void finalizeCalculation(){
        while(containsOperator()) {
            float result;
            Number firstNum, secondNum;
            String calcText = calculator.getCalculationText();
//            char firstOperation = getFirstOperator();
            char firstOperation;
            if(calcText.startsWith("-")) {
                firstOperation = operators.get(1);
            }else{
                firstOperation = operators.get(0);
            }
            //check if first number is negative or not
            String firstNumString;
            if(!calcText.startsWith("-")) {
                 firstNumString = lastNum(calcText.substring(0, calcText.indexOf(String.valueOf(firstOperation))));
            }else{
                firstNumString = "-" + lastNum(calcText.substring(1, calcText.indexOf(String.valueOf(firstOperation))));;
            }
            if (firstNumString.contains(".")) {
                firstNum = Float.valueOf(firstNumString);
            } else {
                firstNum = Integer.valueOf(firstNumString);
            }

            String secondNumString;
            if (operators.size() == 1 || (operators.size() == 2 && calcText.startsWith("-")) ) {
                secondNumString = lastNum(calcText.substring(calcText.indexOf(String.valueOf(firstOperation)) + 1));
            }else {
                secondNumString = firstNum(calcText.substring(calcText.indexOf(String.valueOf(firstOperation)) + 1));
            }

            if (secondNumString.contains(".")) {
                secondNum = Float.valueOf(secondNumString);
            } else {
                secondNum = Integer.valueOf(secondNumString);
            }


            if (firstOperation == '+') {
                result = Float.parseFloat(String.valueOf(firstNum)) + Float.parseFloat(String.valueOf(secondNum));
            } else {
                result = Float.parseFloat(String.valueOf(firstNum)) - Float.parseFloat(String.valueOf(secondNum));
            }

            String oldSubString = String.valueOf(firstNum) + String.valueOf(firstOperation) + String.valueOf(secondNum);
            calculator.setCalculationText(calculator.getCalculationText().replace(oldSubString, String.valueOf(result)));
            mainView.setText(calculator.getCalculationText());
            operators.remove(0);
            //because previousle it removed negative sign
            if(calcText.startsWith("-") && operators.size() <=2) {
                operators.remove(0);
            }
//            mainView.setText(String.valueOf(result));

        }
    }

    private char getFirstHigherOrder(){
        for (char c: operators){
            if (c == '*' || c == '/'){
                return c;
            }
        }
        return '*';
    }

    private char getFirstOperator(){
//        for (char c: operators){
//            if (c == '+' || c == '-'){
//                return c;
//            }
//        }
        for (int i =0; i< operators.size(); i++){
            if (operators.get(i) == '+' || operators.get(i) == '-'){
                //if negative then skip
                if (i==0) {
                    if (!calculator.getCalculationText().startsWith("-") ) {
                        return operators.get(i);
                    }
                }else{
                    return operators.get(i);
                }
            }
        }
        return '+';
    }


    //interface for view
    public interface MainView{
        void clearTextView();
        void setText(String text);
    }

}
