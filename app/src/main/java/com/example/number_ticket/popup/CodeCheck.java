package com.example.number_ticket.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.number_ticket.R;

public class CodeCheck extends DialogFragment {
    static final private String TAG = "CodeCheck";
    public interface OnCompleteListenner{
        void onInputedData(String code);

    }

    private CodeCheck.OnCompleteListenner mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (CodeCheck.OnCompleteListenner) activity;
        }
        catch (ClassCastException e){
            Log.d(TAG, "ㄴㄴ");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_code_check, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.bt_ok);
        final EditText et_codecheck = (EditText) view.findViewById(R.id.et_codecheck);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String code = et_codecheck.getText().toString();
                dismiss();
                mCallback.onInputedData(code);
            }
        });
        return builder.create();
    }
}
