package com.parandak.ensaf8.bookMarkPage;

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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.parandak.ensaf8.R;

public class BookMarkPage extends DialogFragment {

    public static final String TAG = "full_dialog";
    private Context mcontext;
    private Activity mactivity;

    private Toolbar toolbar;

    public static BookMarkPage display(FragmentManager fragmentManager){
        BookMarkPage bookMarkPage = new BookMarkPage();
        bookMarkPage.show(fragmentManager,TAG);
        return bookMarkPage;
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
        View view = inflater.inflate(R.layout.book_mark_page, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        return view;
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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookMarkPage.this.dismiss();
            }
        });
        toolbar.setTitle("Some Title");
        toolbar.inflateMenu(R.menu.example_dialog);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_delete:
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage("DELETE" );
                        builderInner.setTitle("Are you Sure?");
                        builderInner.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"deleteCustomer", Toast.LENGTH_SHORT).show();
                                BookMarkPage.this.dismiss();
                            }
                        });
                        builderInner.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                        break;
                    case R.id.action_add_phone:
                        addPhoneNumDialog();
                        break;                }
                //FullDialog.this.dismiss();
                return true;
            }
        });
        Button btn_add_folder_bookmark = view.findViewById(R.id.btn_add_folder_bookmark);
        btn_add_folder_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"adding bookMarkFolder!!!", Toast.LENGTH_SHORT).show();
                //addBookMarkFolderDialog();
            }
        });
    }
    private void addPhoneNumDialog(){
        //Toast.makeText(getContext(),"adding phone number", Toast.LENGTH_SHORT).show();
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View viewAddPhone = layoutInflater.inflate(R.layout.add_phone_num,null);
        final EditText edi_add_phone = viewAddPhone.findViewById(R.id.edi_add_phone);
        AlertDialog.Builder builderAddPhone = new AlertDialog.Builder(getContext());
        builderAddPhone.setMessage("ADDPhoneNumber" );
        builderAddPhone.setView(viewAddPhone);
        builderAddPhone.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderAddPhone.show();
    }
    private void addBookMarkFolderDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View viewAddBookFolder = layoutInflater.inflate(R.layout.add_book_folder,null);
        final EditText edi_add_book_folder = viewAddBookFolder.findViewById(R.id.edi_add_book_folder);
        AlertDialog.Builder builderAddBookmarkFolder = new AlertDialog.Builder(mcontext);
        builderAddBookmarkFolder.setMessage("ADDBookMarkFolder" );
        builderAddBookmarkFolder.setView(edi_add_book_folder);
        builderAddBookmarkFolder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"add FOLDER : " + edi_add_book_folder.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builderAddBookmarkFolder.show();
    }
}
