package com.skripsigg.heyow.ui.matchchat;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.MessageChatModel;
import com.skripsigg.heyow.models.NotificationData;
import com.skripsigg.heyow.models.NotificationResponse;
import com.skripsigg.heyow.models.NotificationBodyPayload;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.apis.ApiAdapter;
import com.skripsigg.heyow.data.helper.heyow.HeyowApiService;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.views.adapters.MessageChatAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MatchChatActivity extends BaseActivity implements
        MatchChatMvpView,
        View.OnClickListener,
        ChildEventListener {

    private final String TAG = getClass().getSimpleName();

    @Inject
    MatchChatMvpPresenter<MatchChatMvpView> presenter;

    @BindView(R.id.match_chat_toolbar) Toolbar matchChatToolbar;
    @BindView(R.id.match_chat_recycler_view) RecyclerView chatRecyclerView;
    @BindView(R.id.message_type_edit_text) EditText messageTypeEditText;
    @BindView(R.id.message_send_image) ImageView messageSendImage;

    private DatabaseReference chatDbRef;
    private MessageChatAdapter messageChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_chat);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(matchChatToolbar);
        init();
    }

    private void init() {
        initToolbar();
        initRecyclerView();
        initMessageSendClick();

        initMessageAdapter();
        initFirebaseDatabase();
    }

    private void initFirebaseDatabase() {
        chatDbRef = FirebaseDatabase.getInstance()
                .getReference(Constants.DB_CHAT_REF)
                .child(Constants.DB_CHAT_ROOM);
    }

    private void initMessageAdapter() {
        List<MessageChatModel> emptyMessageChat = new ArrayList<MessageChatModel>();
        messageChatAdapter = new MessageChatAdapter(emptyMessageChat);
        chatRecyclerView.setAdapter(messageChatAdapter);
    }

    private void initToolbar() {
        matchChatToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = matchChatToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setHasFixedSize(true);
    }

    private void initMessageSendClick() {
        messageSendImage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatDbRef.child(getMatchDetailExtra().getMatchId())
                .child(Constants.DB_CHAT_MESSAGE).addChildEventListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatDbRef.removeEventListener(this);
        messageChatAdapter.cleanUp();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return String.valueOf(dateFormat.format(Calendar.getInstance().getTime()));
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return String.valueOf(timeFormat.format(Calendar.getInstance().getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view == messageSendImage) {
            if (TextUtils.isEmpty(messageTypeEditText.getText().toString().trim())) {
                Log.d(TAG, "Field is empty");
            } else {
                sendMessageToDb();
            }
        }
    }

    private void sendMessageToDb() {
        String senderMessage = messageTypeEditText.getText().toString().trim();

        if (!senderMessage.isEmpty()) {
            String tempKey = chatDbRef.push().getKey();

            Map<String, String> newMessage = new HashMap<String, String>();
            newMessage.put(Constants.DB_CHAT_MESSAGE_USER_ID, presenter.getUserPreferences().getUserId());
            newMessage.put(Constants.DB_CHAT_MESSAGE_USER_NAME, presenter.getUserPreferences().getUserName());
            newMessage.put(Constants.DB_CHAT_MESSAGE_CONTENT, senderMessage);
            newMessage.put(Constants.DB_CHAT_MESSAGE_DATE, getCurrentDate());
            newMessage.put(Constants.DB_CHAT_MESSAGE_TIME, getCurrentTime());

            chatDbRef.child(getMatchDetailExtra().getMatchId())
                    .child(Constants.DB_CHAT_MESSAGE).child(tempKey).setValue(newMessage);

            messageTypeEditText.getText().clear();

            sendNotification(senderMessage);
        }
    }

    private void sendNotification(String senderMessage) {
        List<String> registIds = new ArrayList<>();

        for (int i = 0; i < getMatchDetailExtra().getJoinedUser().size(); i++) {
            registIds.add(getMatchDetailExtra().getJoinedUser().get(i).getUserToken());

            if (getMatchDetailExtra().getJoinedUser().get(i).getUserToken().equalsIgnoreCase(FirebaseInstanceId.getInstance().getToken())) {
                registIds.remove(FirebaseInstanceId.getInstance().getToken());
            }
        }

        NotificationData notificationData = new NotificationData();
        notificationData.setTitle(presenter.getUserPreferences().getUserName());
        notificationData.setMessage(senderMessage);
        notificationData.setMatchId(getMatchDetailExtra().getMatchId());

        NotificationBodyPayload notificationBodyPayload = new NotificationBodyPayload();
        notificationBodyPayload.setData(notificationData);
        notificationBodyPayload.setRegistrationIds(registIds);

        HeyowApiService apiService = ApiAdapter.getInstance()
                .getRetrofitWithRx(Constants.FCM_API_BASE_URL)
                .create(HeyowApiService.class);

        apiService.sendNotif(notificationBodyPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<NotificationResponse>() {
                    @Override
                    public void onNext(NotificationResponse value) {
                        Log.e("TOTAL SUCCESS", "" + value.getSuccess());
                        Log.e("TOTAL FAILURE", "" + value.getFailure());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ON ERROR", "" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
            try {
                MessageChatModel newMessage = dataSnapshot.getValue(MessageChatModel.class);
                if (newMessage.getUserId().equals(presenter.getUserPreferences().getUserId())) {
                    newMessage.setSenderOrRecipientStatus(Constants.SENDER);
                } else {
                    newMessage.setSenderOrRecipientStatus(Constants.RECIPIENT);
                }
                messageChatAdapter.refillAdapter(newMessage);
                chatRecyclerView.scrollToPosition(messageChatAdapter.getItemCount() - 1);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

    @Override
    public MatchDetailResponse getMatchDetailExtra() {
        return getIntent().getExtras().getParcelable(Constants.KEY_MATCH_RESPONSE_PARCELABLE);
    }
}
