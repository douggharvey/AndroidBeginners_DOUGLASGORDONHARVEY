package douglasharvey.com.myflashcards;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddCardDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    private AddCardDialogListener mListener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the DialogListener so we can send events to the host
            mListener = (AddCardDialogListener) context;
        }catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement AddCardDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_create, null, false);
        final EditText question = (EditText) view.findViewById(R.id.new_question);
        final EditText answer = (EditText) view.findViewById(R.id.new_answer);
        builder.setView(view);
      //  builder.setTitle("Add new Question")
           builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(question.getText().toString(), answer.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddCardDialogFragment.this);
                    }
                });
        return builder.create();
    }


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddCardDialogListener {
        void onDialogPositiveClick(String question, String answer);

        @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
        void onDialogNegativeClick(DialogFragment dialog);
    }

}
