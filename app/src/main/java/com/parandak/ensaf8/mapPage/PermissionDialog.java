package com.parandak.ensaf8.mapPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.fullScreenDialog.FullDialog;
import com.parandak.ensaf8.fullScreenDialog.HistoryTendAdapter;

import java.util.List;

public class PermissionDialog extends DialogFragment {
    public static final String TAG = "Permission_dialog";
    Dialog dialog;
    private Context mcontext;
    private Activity mactivity;
    List<String> mMissingPermissions;
    int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS;


    public PermissionDialog(List<String> mMissingPermissions,int request_code_ask_multi){
        this.mMissingPermissions = mMissingPermissions;
        this.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = request_code_ask_multi;
    }
    public static PermissionDialog display(FragmentManager fragmentManager, List<String> mMissingPermissions,int request_code_ask_multi) {
        PermissionDialog permissionDialog = new PermissionDialog(mMissingPermissions,request_code_ask_multi);
        permissionDialog.show(fragmentManager, TAG);

        return permissionDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.map_page_onboarding, container, false);

        //mactivity.onKeyDown()
        return view;
    }

    public static void myOnKeyDown(int key_code){
        //do whatever you want here
        ///mactivity.finish();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mactivity=  activity;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mcontext = null;
        mactivity = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button)view.findViewById(R.id.button_okay_permission);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMissingPermissions != null && !mMissingPermissions.isEmpty()) {
                    // request permissions
                    String[] params = mMissingPermissions.toArray(new String[mMissingPermissions.size()]);
                    requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
                dismiss();
                Toast.makeText(mcontext, "ok Permission", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
