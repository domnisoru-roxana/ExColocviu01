package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    TextView first_text_view;
    TextView second_text_view;

    int serviceStatus = Constants.SERVICE_STOPPED;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    class ButtonOnClickListener implements View.OnClickListener {

        String text, text2;
        Integer value, value2;
        @Override
        public void onClick(View view) {

            text = first_text_view.getText().toString();
            value = Integer.parseInt(text);

            text2 = second_text_view.getText().toString();
            value2 = Integer.parseInt(text2);

            switch (((Button)view).getId()) {
                case R.id.press_me_button:
                    value++;
                    first_text_view.setText(value.toString());
                    break;
                case R.id.press_me_too_button:
                    value2++;
                    second_text_view.setText(value2.toString());
                    break;
                case R.id.secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra(Constants.SUM_KEY, value + value2);

                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (value + value2 > Constants.LIMIT_VALUE && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUM_KEY, value);
                intent.putExtra(Constants.SECOND_NUM_KEY, value2);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
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

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
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
