package douglasharvey.com.myflashcards;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

import douglasharvey.com.myflashcards.data.CardHelper;

public class WidgetProvider extends AppWidgetProvider {

    private static final String ACTION_OnClickShowAnswer = "OnClickShowAnswerTag";
    private static final String ACTION_OnClickShowQuestion = "OnClickShowQuestionTag";
    private static final String ACTION_OnClickNext = "OnClickNextTag";

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    // NOTE THERE WAS NO NEED TO ADD CUSTOM ACTIONS IN MANIFEST
    @Override
    public void onUpdate(Context context, AppWidgetManager mgr,
                         int[] appWidgetIds) {
        //final int N = appWidgetIds.length;
        for (int appWidgetId : appWidgetIds) {
            ComponentName componentName = new ComponentName(context, getClass());
            mgr.updateAppWidget(componentName, buildUpdate(context));
        }
    }

    private RemoteViews buildUpdate(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
        getRandomQuestion(remoteViews);
        activateQuestionMode(context, remoteViews);
        remoteViews.setOnClickPendingIntent(R.id.btnQAToggle,
                getPendingSelfIntent(context, ACTION_OnClickShowAnswer));

        remoteViews.setOnClickPendingIntent(R.id.btnNextQuestion,
                getPendingSelfIntent(context, ACTION_OnClickNext));

        return remoteViews;
    }

    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        super.onReceive(context, intent);

        if (ACTION_OnClickShowAnswer.equals(intent.getAction())) {
            activateAnswerMode(context, remoteViews);
        } else if (ACTION_OnClickShowQuestion.equals(intent.getAction())) {
            activateQuestionMode(context, remoteViews);
        } else if (ACTION_OnClickNext.equals(intent.getAction())) {
            getRandomQuestion(remoteViews);
            activateQuestionMode(context, remoteViews);
        }

        ComponentName componentName = new ComponentName(context, getClass());
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
    }

    private void activateAnswerMode(Context context, RemoteViews remoteViews) {
        remoteViews.setTextViewText(R.id.btnQAToggle, context.getString(R.string.SeeQuestionButtonText));
        remoteViews.setViewVisibility(R.id.tvAnswer, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.tvQuestion, View.GONE);
        remoteViews.setOnClickPendingIntent(R.id.btnQAToggle,
                getPendingSelfIntent(context, ACTION_OnClickShowQuestion));
    }

    private void activateQuestionMode(Context context, RemoteViews remoteViews) {
        remoteViews.setTextViewText(R.id.btnQAToggle, context.getString(R.string.SeeAnswerButtonText));
        remoteViews.setOnClickPendingIntent(R.id.btnQAToggle,
                getPendingSelfIntent(context, ACTION_OnClickShowAnswer));
        remoteViews.setViewVisibility(R.id.tvAnswer, View.GONE);
        remoteViews.setViewVisibility(R.id.tvQuestion, View.VISIBLE);
    }

    private void getRandomQuestion(RemoteViews remoteViews) {
        ArrayList<Card> cardList;
        CardHelper cardHelper = new CardHelper();

        cardList = cardHelper.getAllCards();

        Random randomValue = new Random();
        int randomIndex = randomValue.nextInt(cardList.size());

        remoteViews.setTextViewText(R.id.tvQuestion, "Q: " + cardList.get(randomIndex).getQuestion());
        remoteViews.setTextViewText(R.id.tvAnswer, "A: " + cardList.get(randomIndex).getAnswer());

    }

}

