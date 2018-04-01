package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    TextView sum_text_view;

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (((Button)view).getId()) {
                case R.id.save_button:
                    setResult(Activity.RESULT_OK, null);
                    finish();
                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, null);
                    finish();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        sum_text_view = (TextView)findViewById(R.id.sum_text_view);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.SUM_KEY)) {
            Integer sum = intent.getIntExtra(Constants.SUM_KEY, 0);
            sum_text_view.setText(String.valueOf(sum));
        }
        else {
            sum_text_view.setText(String.valueOf(0));
        }

        ButtonClickListener buttonClickListener = new ButtonClickListener();
        ((Button)findViewById(R.id.save_button)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.cancel_button)).setOnClickListener(buttonClickListener);
    }
}
