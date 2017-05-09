package douglasharvey.com.myflashcards;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class CardArrayAdapter extends RecyclerView.Adapter<CardArrayAdapter.ViewHolder> {

    private final int listItemLayout;
    private final ArrayList<Card> cardList;
    private final Context context;

    // Constructor of the class
    CardArrayAdapter(Context context, @SuppressWarnings("SameParameterValue") int layoutId, ArrayList<Card> cardList) {
        listItemLayout = layoutId;
        this.cardList = cardList;
        this.context = context;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return cardList == null ? 0 : cardList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        item.setText(cardList.get(listPosition).getQuestion());
    }

    // inner class to initialize the views of rows
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView item;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = (TextView) itemView.findViewById(R.id.row_item);

            // adds ripple effect to touch event
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v
                                .findViewById(R.id.row_content)
                                .getBackground()
                                .setHotspot(event.getX(), event.getY());

                        return (false);
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context.getApplicationContext(), AnswerActivity.class);
            i.putExtra(Intent.EXTRA_TITLE, cardList.get(getLayoutPosition()).getQuestion());
            i.putExtra(Intent.EXTRA_TEXT, cardList.get(getLayoutPosition()).getAnswer());
            context.startActivity(i);
        }
    }
}