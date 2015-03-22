package se.ottomatech.marcusjacobsson.sverigesriksdag.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;

/**
 * Created by Marcus Jacobsson on 2015-03-20.
 */
public class AboutDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getResources().getString(R.string.about_dialog_fragment_pos_btn), this);
        builder.setTitle(getResources().getString(R.string.about_dialog_fragment_title));
        builder.setMessage(getResources().getString(R.string.about_dialog_fragment_message));
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
