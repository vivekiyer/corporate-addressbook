package net.vivekiyer.GAL;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ChoiceDialog implements OnClickListener {
    private static final String ARG_TITLE = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_TITLE"; //$NON-NLS-1$
    private static final String ARG_MESSAGE = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_MESSAGE"; //$NON-NLS-1$
    private static final String ARG_POSITIVE_BUTTON_TEXT = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_POSITIVE_BUTTON_TEXT"; //$NON-NLS-1$
    private static final String ARG_NEGATIVE_BUTTON_TEXT = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_NEGATIVE_BUTTON_TEXT"; //$NON-NLS-1$
    private static final String ARG_POSITIVE_ACTION = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_POSITIVE_ACTION"; //$NON-NLS-1$
    private static final String ARG_NEGATIVE_ACTION = "net.vivekiyer.GAL.ChoiceDialogFragment.ARG_NEGATIVE_ACTION"; //$NON-NLS-1$

    private OnChoiceDialogOptionClickListener clickListener;
    private int positiveAction;
    private int negativeAction;
	private Bundle arguments;
	private Activity parentActivity;

	public void setArguments(Bundle arguments) {
		this.arguments = arguments;
	}

	public Bundle getArguments() {
		return arguments;
	}

	public interface OnChoiceDialogOptionClickListener {
        void onChoiceDialogOptionPressed(int action);
    }

    public static ChoiceDialog newInstance(String title, String message,
    		String positiveButtonText, String negativeButtonText){
    	return newInstance(title, message, positiveButtonText, negativeButtonText, 1, 0);
    }

    public static ChoiceDialog newInstance(String title, String message){
    	return newInstance(title, message, null, null, android.R.id.button1, android.R.id.button2);
    }

	public static ChoiceDialog newInstance(String title, String message,
	                                       String positiveButtonText, String negativeButtonText, int positiveAction, int negativeAction) {
		ChoiceDialog fragment = new ChoiceDialog();

		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		args.putString(ARG_MESSAGE, message);
		if (positiveButtonText != null)
			args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText);
		if (negativeButtonText != null)
			args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText);
		args.putInt(ARG_POSITIVE_ACTION, positiveAction);
		args.putInt(ARG_NEGATIVE_ACTION, negativeAction);
		fragment.setArguments(args);

		return fragment;
	}

    public ChoiceDialog setListener(OnChoiceDialogOptionClickListener clickListener){
        this.clickListener = clickListener;
	    return this;
    }

	public ChoiceDialog setParent(Activity parentActivity) {
		this.parentActivity = parentActivity;
		return this;
	}

    public Dialog create() {

    	Builder builder = new Builder(parentActivity)
    		.setIcon(android.R.drawable.ic_dialog_alert);
    	
        Bundle args = getArguments();
        
        builder.setTitle(args.getString(ARG_TITLE));

        String message = args.getString(ARG_MESSAGE);
        if(message != null && message.length() > 0)
        	builder.setMessage(message);

        String positiveButtonText = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        if (positiveButtonText == null)
        	positiveButtonText = parentActivity.getResources().getString(android.R.string.ok);
        builder.setPositiveButton(positiveButtonText, this);
        
        String negativeButtonText = args.getString(ARG_NEGATIVE_BUTTON_TEXT);
        if(negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, this).create();
        }
 
        positiveAction = args.getInt(ARG_POSITIVE_ACTION);
        negativeAction = args.getInt(ARG_NEGATIVE_ACTION);

        return builder.create();
    }
 
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(this.clickListener != null){
        		this.clickListener.onChoiceDialogOptionPressed(
       				which == DialogInterface.BUTTON_POSITIVE ? positiveAction : negativeAction);
        }
    }
}