package testing.elte.com.calculatormvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import testing.elte.com.calculatormvp.R;
import testing.elte.com.calculatormvp.presenter.CalculatorPrenseter;

public class MainActivity extends AppCompatActivity implements CalculatorPrenseter.MainView {

    CalculatorPrenseter calculatorPrenseter;
    TextView calculationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorPrenseter = new CalculatorPrenseter(this);

        calculationText = (TextView) findViewById(R.id.calculationText);
    }

    @Override
    public void clearTextView() {
        calculationText.setText("0");
    }

    @Override
    public void setText(String text) {
        calculationText.setText(text);
    }

    public void oneClicked(View v){
        calculatorPrenseter.numClicked(1);
    }

    public void twoClicked(View v){
        calculatorPrenseter.numClicked(2);
    }

    public void threeClicked(View v){
        calculatorPrenseter.numClicked(3);
    }

    public void fourClicked(View v){
        calculatorPrenseter.numClicked(4);
    }

    public void fiveClicked(View v){
        calculatorPrenseter.numClicked(5);
    }

    public void sixClicked(View v){
        calculatorPrenseter.numClicked(6);
    }

    public void sevenClicked(View v){
        calculatorPrenseter.numClicked(7);
    }

    public void eightClicked(View v){
        calculatorPrenseter.numClicked(8);
    }

    public void nineClicked(View v){
        calculatorPrenseter.numClicked(9);
    }

    public void zeroClicked(View v){
        calculatorPrenseter.numClicked(0);
    }
    public void dotClicked(View v){
        calculatorPrenseter.dotClicked();
    }
    public void multiplyClicked(View v){
        calculatorPrenseter.operatorClicked("*");
    }
    public void plusClicked(View v){
        calculatorPrenseter.operatorClicked("+");
    }
    public void minusClicked(View v){
        calculatorPrenseter.operatorClicked("-");
    }

    public void devideClicked(View v){
        calculatorPrenseter.operatorClicked("/");
    }

    public void equalClicked(View v){
        calculatorPrenseter.calculate();
    }

    public void clearClicked(View v){
        calculatorPrenseter.clear();
    }

    public void deleteClicked(View v){
        calculatorPrenseter.deleteLast();
    }
}
