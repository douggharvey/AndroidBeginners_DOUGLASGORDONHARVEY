package douglasharvey.com.myflashcards;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import douglasharvey.com.myflashcards.data.CardHelper;
import douglasharvey.com.myflashcards.service.ReminderSchedulerService;

public class MainActivity extends AppCompatActivity
        implements AddCardDialogFragment.AddCardDialogListener {

    private static final String TAG = MainActivity.class.getName();
    private JobScheduler mJobScheduler;
    private ArrayList<Card> cardList = new ArrayList<>();
    private CardArrayAdapter cardArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardHelper cardHelper = new CardHelper();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardList = cardHelper.getAllCards();
        if (cardList.size() == 0) {
            cardHelper.LoadDefaults(getApplicationContext());
            cardList = cardHelper.getAllCards();
        }
        cardArrayAdapter = new CardArrayAdapter(this, R.layout.list_item, cardList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardArrayAdapter);
        recyclerView.setHasFixedSize(true); // for better performance

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCardDialogFragment dialogFragment = new AddCardDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "addCard");
            }
        });
    }


    public void onResume() {
        super.onResume();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        mJobScheduler = (JobScheduler)
                getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancelAll();

        if (prefs.getBoolean(getString(R.string.NotificationsPreference), false)) {
            StartReminderJob();
        }
    }


    private void StartReminderJob() {
        long reminderFrequency = TimeUnit.MINUTES.toMillis(15); // 15 minutes (minimum for Nougat)
        mJobScheduler = (JobScheduler)
                getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        ReminderSchedulerService.class.getName()));
        builder.setPeriodic(reminderFrequency);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        if (mJobScheduler.schedule(builder.build()) <= 0) {
            Log.e(TAG, "onCreate: Some error while scheduling the job");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EditPreferences.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(String question, String answer) {
        int cardListSize;
        Card newCard;
        CardHelper cardHelper = new CardHelper();

        if (!TextUtils.isEmpty(question)) {
            newCard = new Card(question, answer);
            cardHelper.addCard(getApplicationContext(), newCard);
            cardListSize = cardList.size();
            cardList.add(cardListSize, newCard);
            cardArrayAdapter.notifyItemInserted(cardListSize);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

}
