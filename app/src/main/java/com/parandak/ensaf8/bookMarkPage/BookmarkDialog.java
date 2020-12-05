package com.parandak.ensaf8.bookMarkPage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.parandak.ensaf8.R;

public class BookmarkDialog {
    Context context;

    public BookmarkDialog(Context context) {
        this.context = context;
    }

    public void showDialogueADD(String title){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogueView = layoutInflater.inflate(R.layout.bookmark_dialog,null);
        final EditText ediBookmarkDialog = dialogueView.findViewById(R.id.ediBookmarkDialog);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context,"UPDATE TO : " + ediBookmarkDialog.getText().toString() , Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("DELETE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context,"DELETE BOOKMARK"  , Toast.LENGTH_SHORT).show();
                            }
                        }).create();
        alertDialog.setView(dialogueView);
        alertDialog.setTitle(title);
        alertDialog.show();
    }
}
