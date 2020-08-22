package com.borisem.borisemair;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FireMissilesDialogFragment extends DialogFragment {

    public static final String KEY_OK = "key_ok";
    public static final String KEY_CANCEL = "key_cancel";
    public static final String MESSAGE = " ";
    public static final String DIALOGTYPE = "dialogtype";
    RecyclerView recyclerView;
    AdapterBrands adapterBrands;
    ArrayList<Integer> integersBrands;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int textOK = getArguments().getInt(KEY_OK);
        final String textCancel = getArguments().getString(KEY_CANCEL);
        final int message = getArguments().getInt(MESSAGE);
        final int diatype = getArguments().getInt(DIALOGTYPE);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view =  LayoutInflater.from(getContext()).inflate(R.layout.dialog,null);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyle);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        integersBrands= new ArrayList<>();
        integersBrands.add(R.drawable.ic_bmw);
        integersBrands.add(R.drawable.ic_lada);
        integersBrands.add(R.drawable.ic_mercedes);
        integersBrands.add(R.drawable.ic_borisem);


        adapterBrands = new AdapterBrands(view.getContext(), integersBrands, new AdapterBrands.OncheckBrand() {
            @Override
            public void OnCheckBrandLisner(int id) {
                try {
                    onClick.onDialogClickListener(id);
                    dismiss();
                }
                catch (Exception e)
                {
                    dismiss();
                }
            }
        });

        recyclerView.setAdapter(adapterBrands);
        recyclerView.setLayoutManager(linearLayoutManager);

        builder.setView(view);
        builder.setMessage(message)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

        // Create the AlertDialog object and return it
        return builder.create();
    }



    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {

        }
    }

    OnButtonClick onClick;



    public FireMissilesDialogFragment(OnButtonClick _onClick)
    {
        this.onClick = _onClick;
    }
    public FireMissilesDialogFragment( )
    {

    }
    public interface OnButtonClick {
        void onDialogClickListener(int id);
    }
}