package douglasharvey.com.myflashcards;
public class Card {

    private final String question, answer;

    public Card(String q, String a) {
        question = q;
        answer = a;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String toString()
    {
        return "[question = "+question+" + answer = "+answer+", ]";
    }
}