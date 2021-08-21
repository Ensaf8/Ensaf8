package com.parandak.ensaf8.bookMarkPage;

import static com.parandak.ensaf8.homePage.HomePageActivity.WRITE_REQUEST_CODE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.parandak.ensaf8.homePage.HomePageActivity;
import com.parandak.ensaf8.storage.EnsafQueryExport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookMarkPage extends DialogFragment {

    public static final String TAG = "full_dialog";
    private Context mcontext;
    private Activity mactivity;
    String title;
    boolean isEdit;
    BookMarkPage bookMarkPage = this;

    private Toolbar toolbar;
    RecyclerView recyclerView;
    BookMarkFolderAdapter bookMarkFolderAdapter;

    List<BookMarkFolder> bookMarkFolderList = new ArrayList<>();

    String bTypeID;

    public BookMarkPage displayBookMarkFolder(FragmentManager fragmentManager){
        BookMarkPage bookMarkPage = new BookMarkPage();
        bookMarkPage.show(fragmentManager,TAG);
        return bookMarkPage;
    }
    public BookMarkPage(){
        this.title = "BooK Mark Folder";
        this.isEdit = true;
    }

    public BookMarkPage(String title){
        this.title = title;
        this.isEdit = false;
    }

    public BookMarkPage export(FragmentManager fragmentManager,String title){
        BookMarkPage bookMarkPage = new BookMarkPage(title);
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
        Log.d("ensaf::::::::", TAG + "> onViewCreated title : " + title);
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookMarkPage.this.dismiss();
            }
        });
        toolbar.setTitle(title);
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
                if (isEdit){
                    showDialogueEDITE(bookMarkFolderList.get(position).getId() ,bookMarkFolderList.get(position).getTitle());
                }else {
                    SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    Date c = Calendar.getInstance().getTime();
                    String formattedDate = df.format(c);
                    String Filename = "SyncFile : " + formattedDate +".txt";
                    bTypeID = bookMarkFolderList.get(position).getId();
                    createFile(HomePageActivity.mimeType,Filename);
                    Toast.makeText(mcontext, "Item clicked !!!! " + bookMarkFolderList.get(position).getId(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private void createFile(String mimeType, String fileName) {
        int WRITE_REQUEST_CODE = HomePageActivity.WRITE_REQUEST_CODE;
        //Intent intent = mactivity.getIntent();
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.putExtra("B_type_id","2020");
        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WRITE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            BookMarkPageQuery bookMarkPageQuery = new BookMarkPageQuery();
            final Uri treeUri = data.getData();
            //alterDocument(treeUri,ensafQueryExport.exportQuery());
            alterDocument(treeUri,"TEST Export => the query is : " + bookMarkPageQuery.getPhaseBookMark(bTypeID));
            //alterDocument(treeUri,ensafQueryExport.dailyReport());
        }
    }
    private void alterDocument(Uri uri,String txt) {
        try {
            ParcelFileDescriptor pfd = mcontext.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            //fileOutputStream.write(("Overwritten by MyCloud at " +
            //System.currentTimeMillis() + "\n").getBytes());
            fileOutputStream.write((txt+"\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Toast.makeText(mcontext, "Sync File Created !", Toast.LENGTH_SHORT).show();
        }
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
