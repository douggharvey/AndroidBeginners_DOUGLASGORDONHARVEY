package douglasharvey.com.myflashcards.data;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

class JsonReader {
    static String loadJSONFromAsset(Context context, @SuppressWarnings("SameParameterValue") String filePath) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(buffer); // no need for result
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
