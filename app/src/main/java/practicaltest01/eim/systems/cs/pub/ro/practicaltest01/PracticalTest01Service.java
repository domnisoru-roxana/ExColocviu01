package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Service extends Service {
    public PracticalTest01Service() {
    }

    private ProcessingThread processingThread;

    @Override
    public void onCreate() {
        super.onCreate();
        // ...
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        int value = 0, value2 = 0, ma, mg;
        if (intent.getExtras().containsKey(Constants.FIRST_NUM_KEY)) {
            value = intent.getIntExtra(Constants.FIRST_NUM_KEY, 0);
        }
        if (intent.getExtras().containsKey(Constants.SECOND_TEXT_KEY)) {
            value = intent.getIntExtra(Constants.SECOND_NUM_KEY, 0);
        }

        processingThread = new ProcessingThread(this, value, value2);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // ...
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        processingThread.stopThread();
    }
}
