package com.parandak.ensaf8.bookMarkPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.bookMarkPage.rv.BookMarkFolder;
import com.parandak.ensaf8.bookMarkPage.rv.BookMarkFolderAdapter;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkTypeRepo;
import com.parandak.ensaf8.fullScreenDialog.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BookMarkPage extends DialogFragment {

    public static final String TAG = "full_dialog";
    private Context mcontext;
    private Activity mactivity;
    BookMarkPage bookMarkPage = this;

    private Toolbar toolbar;
    RecyclerView recyclerView;
    BookMarkFolderAdapter bookMarkFolderAdapter;

    List<BookMarkFolder> bookMarkFolderList = new ArrayList<>();

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
        Log.d("ensaf::::::::", TAG + "> onCreateView");
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
        Log.d("ensaf::::::::", TAG + "> onViewCreated");
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
        btnAddBookMarkFolder(view);
        initBookMarkType();
        bookMarkFolder(view);

    }
    void bookMarkFolder(View view){
        recyclerView = view.findViewById(R.id.rv_bookmarkPage);
        recyclerView.setHasFixedSize(true);
        bookMarkFolderAdapter = new BookMarkFolderAdapter(bookMarkFolderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(mcontext, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookMarkFolderAdapter);
        bookMarkFolderAdapter.setOnItemClickListener(new BookMarkFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mcontext, "Item clicked !!!! " + bookMarkFolderList.get(position).getId(), Toast.LENGTH_SHORT).show();
                showDialogueEDITE(bookMarkFolderList.get(position).getId() ,bookMarkFolderList.get(position).getTitle());
            }
        });
    }
    private void initBookMarkType(){
        Log.d("ensaf::::::::", TAG + "> initBookMarkType");
        BookMarkPageQuery bookMarkPageQuery = new BookMarkPageQuery();
        Cursor cursorBMtype = bookMarkPageQuery.getBookMarkType();
        bookMarkFolderList.clear();
        if (cursorBMtype.moveToFirst()){
            do{
                bookMarkFolderList.add(new BookMarkFolder(cursorBMtype.getString(0),cursorBMtype.getString(1)));
            }while (cursorBMtype.moveToNext());
        }
    }
    private void btnAddBookMarkFolder(final View view){
        Button btn_add_folder_bookmark = view.findViewById(R.id.btn_add_folder_bookmark);
        btn_add_folder_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookMarkFolderDialog();
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
        builderAddBookmarkFolder.setView(viewAddBookFolder);
        builderAddBookmarkFolder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BookMarkTypeRepo bookMarkTypeRepo = new BookMarkTypeRepo();
                BookMarkType bookMarkType = new BookMarkType();
                bookMarkType.setTitle(edi_add_book_folder.getText().toString());
                int i = bookMarkTypeRepo.insert(bookMarkType);
                if (i>0){

                    initBookMarkType();
                    bookMarkFolderAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"add FOLDER : " + edi_add_book_folder.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderAddBookmarkFolder.show();
    }
    public void showDialogueEDITE(final String id,final String title){
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View dialogueView = layoutInflater.inflate(R.layout.bookmark_dialog,null);
        final EditText ediBookmarkDialog = dialogueView.findViewById(R.id.ediBookmarkDialog);
        ediBookmarkDialog.setText(title);
        AlertDialog alertDialog = new AlertDialog.Builder(mcontext)
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BookMarkTypeRepo bookMarkTypeRepo = new BookMarkTypeRepo();
                                BookMarkType bookMarkType = new BookMarkType();
                                bookMarkType.setId(id);
                                bookMarkType.setTitle(ediBookmarkDialog.getText().toString());
                                if (bookMarkTypeRepo.update(bookMarkType)){
                                    initBookMarkType();
                                    bookMarkFolderAdapter.notifyDataSetChanged();
                                    Toast.makeText(mcontext,"UPDATE TO : " + ediBookmarkDialog.getText().toString() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("DELETE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(mcontext);
                                builderInner.setMessage("DELETE Folder : " + title);
                                builderInner.setTitle("Are you Sure?");
                                builderInner.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        BookMarkTypeRepo bookMarkTypeRepo = new BookMarkTypeRepo();
                                        if (bookMarkTypeRepo.delete_ID_BookMarkType(id)){
                                            initBookMarkType();
                                            bookMarkFolderAdapter.notifyDataSetChanged();
                                            Toast.makeText(mcontext,"BOOKMARK DELETEed !"  , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builderInner.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builderInner.show();

                            }
                        }).create();
        alertDialog.setView(dialogueView);
        alertDialog.setTitle(id + " : " + title);
        alertDialog.show();
    }
}
