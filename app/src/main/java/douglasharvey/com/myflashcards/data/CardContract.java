package douglasharvey.com.myflashcards.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

class CardContract {

    static final String CONTENT_AUTHORITY = "douglasharvey.com.myflashcards";

    private static final Uri     BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_CARD = "flashcards";

    static final class CardEntry implements BaseColumns {
        static final String TABLE_NAME = "flashcards";

        static final String COLUMN_QUESTION="question";
        static final String COLUMN_ANSWER="answer";

        static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARD).build();
        static final String CONTENT_TYPE      = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CARD;

        static Uri buildCardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
