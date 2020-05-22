package com.example.number_ticket.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.number_ticket.data.ServiceInfo;
import com.example.number_ticket.manager.AddShop;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddServicePopup extends DialogFragment {
    static final String TAG = "AddServicePopup";
    public interface OnCompleteListenner{
        void onInputedData(String service, String time);
    }

    private OnCompleteListenner mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnCompleteListenner) activity;
        }
        catch (ClassCastException e){
            Log.d("DialogFragmentExample", "ㄴㄴ");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_service_popup, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.bt_ok);
        final EditText service_name = (EditText) view.findViewById(R.id.et_service_name);
        final EditText service_time = (EditText) view.findViewById(R.id.et_service_time);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String service = service_name.getText().toString();
                String time = service_time.getText().toString();
                Log.d(TAG, service + "aaa" + time);
                dismiss();
                mCallback.onInputedData(service, time);
            }
        });
        return builder.create();
    }
}
