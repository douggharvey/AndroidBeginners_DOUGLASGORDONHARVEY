package douglasharvey.com.myflashcards.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import douglasharvey.com.myflashcards.SendNotification;

public class ReminderSchedulerService extends JobService {
    private static final String TAG = "ReminderSchedulerSrvice";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob:");
        SendNotification sendNotification = new SendNotification();
        sendNotification.CreateNotification(this);
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob:");
        return false;
    }


}
