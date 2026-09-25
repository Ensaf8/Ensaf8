package com.parandak.ensaf8.mapPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.searchPage.SearchPageActivity;
import com.parandak.ensaf8.tendHistoryDialog.TendHistoryDialog;

/**
 * MapBottomSheetManager – Phase 1 extraction (behavior-preserving).
 *
 * Extracted (safe):
 *  - View binding
 *  - Add Customer, container click, RatingBar
 *  - Reminder click / long-click
 *  - BottomSheetBehavior setup
 *
 * Deferred (stay in Activity via Callback):
 *  - Bookmark, Attendance, History, Delete, Edit
 *  - All shared state ownership
 */
public class MapBottomSheetManager {

    // ──────────────────────────────────────────────────────────────
    // Callback – Activity keeps ownership of shared state & high-risk logic
    // ──────────────────────────────────────────────────────────────
    public interface Callback {
        String getIdConsSelected();
        boolean isConnected();
        void onRatingChanged(float rating, boolean fromUser);
        void onBottomSheetHidden();
        void onBottomSheetStateChanged(int newState);

        // Deferred high-risk listeners
        void onBookmarkCheckedChanged(boolean isChecked);
        void onBookmarkLongClick();
        void onDeleteRequested();
        void onAttendanceClick();
        void onAttendanceLongClick();
        void onStatusDataClick();
        void onEditClick();
    }

    // ──────────────────────────────────────────────────────────────
    // Fields
    // ──────────────────────────────────────────────────────────────
    private final Context context;
    private final Activity activity;
    private final Callback callback;

    private LinearLayout bottom_container;
    private BottomSheetBehavior<?> mBottomSheetBehaviour;

    private Button button_edit;
    private Button bottom_sheet_status_data;
    private ImageButton bottom_sheet_add_reminder;
    private ImageButton bottom_sheet_attendance;
    private ImageButton button_add_customer;
    private ImageButton bottom_tend_history;
    private FloatingActionButton fab_map;

    private CheckBox checkBoxBookmark;
    private TextView txt_bottom_book_type;
    private RatingBar ratingBottom;
    private EditText bottom_sheet_name;

    // ──────────────────────────────────────────────────────────────
    // Constructor
    // ──────────────────────────────────────────────────────────────
    public MapBottomSheetManager(Context context, Activity activity, Callback callback) {
        this.context = context;
        this.activity = activity;
        this.callback = callback;
    }

    // ──────────────────────────────────────────────────────────────
    // Public API
    // ──────────────────────────────────────────────────────────────
    public void setup(View root, FloatingActionButton fabMap) {
        this.fab_map = fabMap;

        // View binding
        button_edit = root.findViewById(R.id.button_edit);
        checkBoxBookmark = root.findViewById(R.id.checkBoxBookmark);
        txt_bottom_book_type = root.findViewById(R.id.txt_bottom_book_type);
        bottom_sheet_name = root.findViewById(R.id.bottom_sheet_name);
        bottom_container = root.findViewById(R.id.bottom_container);

        bottom_sheet_status_data = root.findViewById(R.id.btn_date);
        bottom_sheet_add_reminder = root.findViewById(R.id.bottom_sheet_add_reminder);
        bottom_sheet_attendance = root.findViewById(R.id.bottom_sheet_attendance);
        button_add_customer = root.findViewById(R.id.button_add_customer);
        bottom_tend_history = root.findViewById(R.id.bottom_tend_history);

        ratingBottom = root.findViewById(R.id.ratingBottom);

        // Safe listeners
        wireAddCustomer();
        wireContainerClick();
        wireRatingBar();
        wireReminder();

        // Deferred listeners → forward to Activity
        wireDeferredListeners();

        // Behavior
        View nestedScrollView = root.findViewById(R.id.nestedScrollView);
        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehaviour.setBottomSheetCallback(createBehaviorCallback());
    }

    public BottomSheetBehavior<?> getBehavior() {
        return mBottomSheetBehaviour;
    }

    public boolean isHidden() {
        return mBottomSheetBehaviour != null
                && mBottomSheetBehaviour.getState() == BottomSheetBehavior.STATE_HIDDEN;
    }

    public void setState(int state) {
        if (mBottomSheetBehaviour != null) {
            mBottomSheetBehaviour.setState(state);
        }
    }

    public void hide() {
        setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void collapse() {
        setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    // Accessors so Activity can still populate the views
    public EditText getBottomSheetName() { return bottom_sheet_name; }
    public Button getStatusDataButton() { return bottom_sheet_status_data; }
    public RatingBar getRatingBar() { return ratingBottom; }
    public CheckBox getBookmarkCheckBox() { return checkBoxBookmark; }
    public TextView getBookmarkTypeText() { return txt_bottom_book_type; }
    public Button getEditButton() { return button_edit; }

    // ──────────────────────────────────────────────────────────────
    // Safe Phase-1 listeners
    // ──────────────────────────────────────────────────────────────
    private void wireAddCustomer() {
        button_add_customer.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchPageActivity.class);
            intent.putExtra("ID", callback.getIdConsSelected());
            context.startActivity(intent);
        });
    }

    private void wireContainerClick() {
        bottom_container.setOnClickListener(v -> ratingBottom.setRating(0));
    }

    private void wireRatingBar() {
        ratingBottom.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
                callback.onRatingChanged(rating, fromUser));
    }

    private void wireReminder() {
        bottom_sheet_add_reminder.setOnClickListener(v -> {
            if (callback.isConnected()) {
                AddReminderDialouge dialog = new AddReminderDialouge(context, activity);
                dialog.showDialogueADD(callback.getIdConsSelected());
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "SignIn First !", Toast.LENGTH_SHORT).show();
            }
        });

        bottom_sheet_add_reminder.setOnLongClickListener(v -> {
            TendHistoryDialog dialog = new TendHistoryDialog(
                    context, activity, callback.getIdConsSelected());
            dialog.showDialogHistory();
            return true;
        });
    }

    // ──────────────────────────────────────────────────────────────
    // Deferred listeners – only forward to Activity
    // ──────────────────────────────────────────────────────────────
    private void wireDeferredListeners() {
        checkBoxBookmark.setOnCheckedChangeListener((buttonView, isChecked) ->
                callback.onBookmarkCheckedChanged(isChecked));

        checkBoxBookmark.setOnLongClickListener(v -> {
            callback.onBookmarkLongClick();
            return false;
        });

        bottom_tend_history.setOnClickListener(v -> callback.onDeleteRequested());
        bottom_sheet_attendance.setOnClickListener(v -> callback.onAttendanceClick());
        bottom_sheet_attendance.setOnLongClickListener(v -> {
            callback.onAttendanceLongClick();
            return true;
        });
        bottom_sheet_status_data.setOnClickListener(v -> callback.onStatusDataClick());
        button_edit.setOnClickListener(v -> callback.onEditClick());
    }

    // ──────────────────────────────────────────────────────────────
    // Behavior callback
    // ──────────────────────────────────────────────────────────────
    private BottomSheetBehavior.BottomSheetCallback createBehaviorCallback() {
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                        callback.onBottomSheetStateChanged(newState);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        if (fab_map != null) fab_map.hide();
                        callback.onBottomSheetStateChanged(newState);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if (fab_map != null) fab_map.hide();
                        callback.onBottomSheetStateChanged(newState);
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        if (fab_map != null) fab_map.show();
                        // Clear only UI shell views
                        if (bottom_sheet_name != null) bottom_sheet_name.setText("");
                        if (bottom_sheet_status_data != null) bottom_sheet_status_data.setText("");
                        if (checkBoxBookmark != null) checkBoxBookmark.setChecked(false);
                        if (txt_bottom_book_type != null) txt_bottom_book_type.setText("پیش فرض");
                        if (ratingBottom != null) ratingBottom.setRating(0);

                        callback.onBottomSheetHidden();
                        callback.onBottomSheetStateChanged(newState);
                        break;

                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        if (fab_map != null) fab_map.hide();
                        callback.onBottomSheetStateChanged(newState);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float slideOffset) {
                // empty – same as original
            }
        };
    }
}