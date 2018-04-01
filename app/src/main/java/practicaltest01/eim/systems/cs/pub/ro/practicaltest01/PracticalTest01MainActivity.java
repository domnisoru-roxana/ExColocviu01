package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    TextView first_text_view;
    TextView second_text_view;

    class ButtonOnClickListener implements View.OnClickListener {

        String text, text2;
        Integer value, value2;
        @Override
        public void onClick(View view) {
            switch (((Button)view).getId()) {
                case R.id.press_me_button:
                    text = first_text_view.getText().toString();
                    value = Integer.parseInt(text);
                    value++;
                    first_text_view.setText(value.toString());
                    break;
                case R.id.press_me_too_button:
                    text2 = second_text_view.getText().toString();
                    value2 = Integer.parseInt(text2);
                    value2++;
                    second_text_view.setText(value2.toString());
                    break;
                case R.id.secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);

                    text = first_text_view.getText().toString();
                    value = Integer.parseInt(text);
                    text2 = second_text_view.getText().toString();
                    value2 = Integer.parseInt(text2);

                    intent.putExtra(Constants.SUM_KEY, value + value2);

                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        first_text_view = (TextView)findViewById(R.id.first_text_view);
        second_text_view = (TextView)findViewById(R.id.second_text_view);

        ButtonOnClickListener listener = new ButtonOnClickListener();

        ((Button)findViewById(R.id.press_me_button)).setOnClickListener(listener);
        ((Button)findViewById(R.id.press_me_too_button)).setOnClickListener(listener);
        ((Button)findViewById(R.id.secondary_activity_button)).setOnClickListener(listener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.FIRST_TEXT_KEY)) {
                first_text_view.setText(savedInstanceState.getString(Constants.FIRST_TEXT_KEY));
            }
            else {
                first_text_view.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey(Constants.SECOND_TEXT_KEY)) {
                second_text_view.setText(savedInstanceState.getString(Constants.SECOND_TEXT_KEY));
            }
            else {
                second_text_view.setText(String.valueOf(0));
            }
        }
        else {
            first_text_view.setText(String.valueOf(0));
            second_text_view.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.FIRST_TEXT_KEY, first_text_view.getText().toString());
        savedInstanceState.putString(Constants.SECOND_TEXT_KEY, second_text_view.getText().toString());
}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.FIRST_TEXT_KEY)) {
            first_text_view.setText(savedInstanceState.getString(Constants.FIRST_TEXT_KEY));
        }
        else {
            first_text_view.setText(Integer.toString(0));
        }
        if (savedInstanceState.containsKey(Constants.SECOND_TEXT_KEY)) {
            second_text_view.setText(savedInstanceState.getString(Constants.SECOND_TEXT_KEY));
        }
        else {
            second_text_view.setText(String.valueOf(0));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.SECONDARY_ACTIVITY_REQUEST_CODE:
                Toast.makeText(this, "return code: " + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
