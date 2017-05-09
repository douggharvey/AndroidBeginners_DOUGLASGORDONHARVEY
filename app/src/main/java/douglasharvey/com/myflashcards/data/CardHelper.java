package douglasharvey.com.myflashcards.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import douglasharvey.com.myflashcards.Card;
import douglasharvey.com.myflashcards.data.CardContract.CardEntry;

import static android.content.ContentValues.TAG;

public class CardHelper {

    public void addCard (Context context, Card card) {
        context.getContentResolver().insert(CardEntry.CONTENT_URI, getCardContentValues(card));
    }

    private ContentValues getCardContentValues(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CardEntry.COLUMN_QUESTION, card.getQuestion());
        contentValues.put(CardEntry.COLUMN_ANSWER, card.getAnswer());
        return contentValues;
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cardList = new ArrayList<>();
        CardProvider cardprovider = new CardProvider();
        Cursor cursor = cardprovider.query(CardEntry.CONTENT_URI,
                null, null, null, null
                );

        String question, answer;

        try {
            //noinspection ConstantConditions
            if (cursor.moveToFirst()) {
                do {
                    question = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_QUESTION));
                    answer = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_ANSWER));
                    cardList.add(new Card(question, answer));

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get cards from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return cardList;

    }

    public void LoadDefaults(Context context) { // small json - no need for asynctask
        ArrayList<Card> cardList;
        CardHelper cardHelper = new CardHelper();

        String cardsJson = JsonReader.loadJSONFromAsset(context, "defaultquestions.json");
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(cardsJson, Card[].class);
        cardList = new ArrayList<>(Arrays.asList(cards));

        for (Card card : cardList) {
            cardHelper.addCard(context, card);
        }

    }



}
