package com.app.sigap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.master.MainMenuActivity;
import com.app.sources.ChatIDE;
import com.app.utility.ChatInfoHelper;
import com.app.utility.FileDownloadHelper;
import com.app.utility.KeyboardHelper;
import com.app.utility.MediaHelper;
import com.app.utility.PermissionHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.AdminMessage;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.FileMessage;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LiveChatActivity extends AppCompatActivity {

    private enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    private static final String identifier = "SendBirdGroupChat";
    private static final int REQUEST_PICK_IMAGE = 100;
    private static final int REQUEST_INVITE_USERS = 200;

    private TextView txtChannelName;
    private EditText txtMessage;
    private Button btnSend;
    private ImageButton btnUpload;
    private ProgressBar progressBarUpload;
    private ImageButton btnClose;
    private LiveChatActivity.SendBirdMessagingAdapter mAdapter;
    private ListView mListView;

    private String adminUserId;
    private String sUserId;
    private GroupChannel mGroupChannel;
    private String mGroupChannelUrl;
    private GroupChannelListQuery mQuery;
    private PreviousMessageListQuery mPrevMessageListQuery;
    private boolean mIsUploading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.sendbird_fragment_group_chat);

        initUI();
        initSendBird();

        adminUserId = ChatIDE.DEFAULT_ADMIN;
        sUserId = "mike";

        connect();
    }

    private void initUI() {
        // control initialization
        txtChannelName = (TextView) findViewById(R.id.txt_channel_name);
        txtMessage = (EditText) findViewById(R.id.etxt_message);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnUpload = (ImageButton) findViewById(R.id.btn_upload);
        progressBarUpload = (ProgressBar) findViewById(R.id.progress_btn_upload);
        btnClose = (ImageButton) findViewById(R.id.btn_close);
        mListView = (ListView) findViewById(R.id.list);

        // hide the upload functionality
        btnUpload.setVisibility(View.GONE);
        progressBarUpload.setVisibility(View.GONE);

        // control listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();

                Intent intent = new Intent(LiveChatActivity.this, MainMenuActivity.class);

                startActivity(intent);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectImageActivity();
            }
        });

        txtMessage.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        txtMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        sendMessage();
                    }
                    return true; // Do not hide keyboard.
                }

                return false;
            }
        });
        txtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                btnSend.setEnabled(s.length() > 0);
//
//                if (s.length() == 1) {
//                    mGroupChannel.startTyping();
//                } else if (s.length() <= 0) {
//                    mGroupChannel.endTyping();
//                }
            }
        });

        // typeface settings
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/titillium_regular_webfont.ttf");

        txtChannelName.setTypeface(typeface);
        txtMessage.setTypeface(typeface);
        btnSend.setTypeface(typeface);
    }

    private void showSelectImageActivity() {
        if (PermissionHelper.requestReadWriteStoragePermissions(this)) {
            mIsUploading = true;

            Intent intent = new Intent();

            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    REQUEST_PICK_IMAGE);

            /**
             * Set this as false to maintain SendBird connection,
             * even when an external Activity is started.
             */
            SendBird.setAutoBackgroundDetection(false);
        }
    }

    private void initSendBird() {
        SendBird.init(ChatIDE.API_ID, this);
    }

    private void connect() {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(final User user, SendBirdException e) {
                if (e != null) {
                    showToastMessage("" + e.getCode() + ":" + e.getMessage());
                    setState(State.DISCONNECTED);

                    return;
                }

                String nickname = user.getNickname();

                SendBird.updateCurrentUserInfo(nickname, null, new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {
                            showToastMessage("" + e.getCode() + ":" + e.getMessage());
                            setState(State.DISCONNECTED);

                            return;
                        }

                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();

                        editor.putString("user_id", user.getUserId());
                        editor.putString("nickname", user.getNickname());
                        editor.commit();

                        setState(State.CONNECTED);
                    }
                });

                if (FirebaseInstanceId.getInstance().getToken() == null) {
                    return;
                }

                SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(),
                        new SendBird.RegisterPushTokenWithStatusHandler() {
                            @Override
                            public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus,
                                                     SendBirdException e) {
                                if (e != null) {
                                    showToastMessage("" + e.getCode() + ":" + e.getMessage());

                                    return;
                                }
                            }
                        });

                final String groupChannelName = String.format("personal-channel-%s-%s",
                        user.getUserId(), adminUserId); //String.valueOf(new Date().getTime())

                ArrayList users = new ArrayList<>();

                users.add(user.getUserId());
                users.add(adminUserId);

                //((TextView) getActivity().findViewById(R.id.txt_channel_name)).setText(Helper.getDisplayMemberNames(mGroupChannel.getMembers(), true));

                GroupChannel.createChannelWithUserIds(users, true, groupChannelName,
                        null, null, new GroupChannel.GroupChannelCreateHandler() {
                            @Override
                            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                                if (e != null) {
                                    Toast.makeText(getApplicationContext(),
                                            "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mGroupChannel = groupChannel;
                                mGroupChannelUrl = groupChannel.getUrl();

                                mAdapter = new LiveChatActivity.SendBirdMessagingAdapter(LiveChatActivity.this, mGroupChannel);

                                mListView.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();

                                loadPrevMessages(true);
                            }
                        });
            }
        });

        setState(State.CONNECTING);
    }

    private void setState(State state) {
        switch (state) {
            case DISCONNECTED:
            case CONNECTING:
                findViewById(R.id.btn_send).setEnabled(false);
                break;
            case CONNECTED:
                findViewById(R.id.btn_send).setEnabled(true);
                break;
        }
    }

    private void initGroupChannel(String groupChannelUrl) {
        GroupChannel.getChannel(groupChannelUrl, new GroupChannel.GroupChannelGetHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    showToastMessage("" + e.getCode() + ":" + e.getMessage());

                    return;
                }

                mGroupChannel = groupChannel;
                mGroupChannel.markAsRead();
            }
        });
    }

    private void loadPrevMessages(final boolean refresh) {
        if (mGroupChannel == null) {
            return;
        }

        if (refresh || mPrevMessageListQuery == null) {
            mPrevMessageListQuery = mGroupChannel.createPreviousMessageListQuery();
        }

        if (mPrevMessageListQuery.isLoading()) {
            return;
        }

        if (!mPrevMessageListQuery.hasMore()) {
            return;
        }

        mPrevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> list, SendBirdException e) {
                if (e != null) {
                    showToastMessage("" + e.getCode() + ":" + e.getMessage());

                    return;
                }

                if (refresh) {
                    mAdapter.clear();
                }

                for (BaseMessage message : list) {
                    mAdapter.insertMessage(message);
                }

                mAdapter.notifyDataSetChanged();
                mListView.setSelection(list.size());
            }
        });
    }

    private void showUploadProgress(boolean tf) {
        if (tf) {
            btnUpload.setEnabled(false);
            btnUpload.setVisibility(View.INVISIBLE);
            progressBarUpload.setVisibility(View.VISIBLE);
        } else {
            btnUpload.setEnabled(true);
            btnUpload.setVisibility(View.VISIBLE);
            progressBarUpload.setVisibility(View.GONE);
        }
    }

    private void turnOffListViewDecoration(ListView listView) {
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setHorizontalFadingEdgeEnabled(false);
        listView.setVerticalFadingEdgeEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setVerticalScrollBarEnabled(true);
        listView.setSelector(new ColorDrawable(0x00ffffff));
        listView.setCacheColorHint(0x00000000); // For Gingerbread scrolling bug fix
    }

    private void disconnect() {
        SendBird.disconnect(new SendBird.DisconnectHandler() {
            @Override
            public void onDisconnected() {
                setState(State.DISCONNECTED);
            }
        });
    }

    private void sendMessage() {
        if (txtMessage.getText().length() <= 0) {
            return;
        }

        mGroupChannel.sendUserMessage(txtMessage.getText().toString(), new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (e != null) {
                    String errorMessage = "" + e.getCode() + ":" + e.getMessage();

                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                mAdapter.appendMessage(userMessage);
                mAdapter.notifyDataSetChanged();

                txtMessage.setText("");
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            KeyboardHelper.hideKeyboard(this);
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(LiveChatActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public static class SendBirdMessagingAdapter extends BaseAdapter {
        private static final int TYPE_UNSUPPORTED = 0;
        private static final int TYPE_USER_MESSAGE = 1;
        private static final int TYPE_ADMIN_MESSAGE = 2;
        private static final int TYPE_FILE_MESSAGE = 3;
        private static final int TYPE_TYPING_INDICATOR = 4;

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final ArrayList<Object> mItemList;
        private final GroupChannel mGroupChannel;

        public SendBirdMessagingAdapter(Context context, GroupChannel channel) {
            mContext = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItemList = new ArrayList<>();
            mGroupChannel = channel;
        }

        @Override
        public int getCount() {
            return mItemList.size() + (mGroupChannel.isTyping() ? 1 : 0);
        }

        @Override
        public Object getItem(int position) {
            if (position >= mItemList.size()) {
                List<User> members = mGroupChannel.getTypingMembers();
                ArrayList<String> names = new ArrayList<>();
                for (User member : members) {
                    names.add(member.getNickname());
                }

                return names;
            }
            return mItemList.get(position);
        }

        public void delete(Object object) {
            mItemList.remove(object);
        }

        public void clear() {
            mItemList.clear();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void insertMessage(BaseMessage message) {
            mItemList.add(0, message);
        }

        public void appendMessage(BaseMessage message) {
            mItemList.add(message);
        }

        @Override
        public int getItemViewType(int position) {
            if (position >= mItemList.size()) {
                return TYPE_TYPING_INDICATOR;
            }

            Object item = mItemList.get(position);
            if (item instanceof UserMessage) {
                return TYPE_USER_MESSAGE;
            } else if (item instanceof FileMessage) {
                return TYPE_FILE_MESSAGE;
            } else if (item instanceof AdminMessage) {
                return TYPE_ADMIN_MESSAGE;
            }

            return TYPE_UNSUPPORTED;
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LiveChatActivity.SendBirdMessagingAdapter.ViewHolder viewHolder;
            final Object item = getItem(position);

            if (convertView == null || ((LiveChatActivity.SendBirdMessagingAdapter.ViewHolder) convertView.getTag()).getViewType() != getItemViewType(position)) {
                viewHolder = new LiveChatActivity.SendBirdMessagingAdapter.ViewHolder();
                viewHolder.setViewType(getItemViewType(position));

                switch (getItemViewType(position)) {
                    case TYPE_UNSUPPORTED:
                        convertView = new View(mInflater.getContext());
                        convertView.setTag(viewHolder);
                        break;
                    case TYPE_USER_MESSAGE: {
                        TextView tv;
                        ImageView iv;
                        View v;

                        convertView = mInflater.inflate(com.sendbird.android.sample.R.layout.sendbird_view_group_user_message, parent, false);

                        v = convertView.findViewById(com.sendbird.android.sample.R.id.left_container);
                        viewHolder.setView("left_container", v);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_left_thumbnail);
                        viewHolder.setView("left_thumbnail", iv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_left);
                        viewHolder.setView("left_message", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_left_name);
                        viewHolder.setView("left_name", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_left_time);
                        viewHolder.setView("left_time", tv);

                        v = convertView.findViewById(com.sendbird.android.sample.R.id.right_container);
                        viewHolder.setView("right_container", v);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_right_thumbnail);
                        viewHolder.setView("right_thumbnail", iv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right);
                        viewHolder.setView("right_message", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_name);
                        viewHolder.setView("right_name", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_time);
                        viewHolder.setView("right_time", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_status);
                        viewHolder.setView("right_status", tv);

                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_ADMIN_MESSAGE: {
                        convertView = mInflater.inflate(com.sendbird.android.sample.R.layout.sendbird_view_admin_message, parent, false);
                        viewHolder.setView("message", convertView.findViewById(com.sendbird.android.sample.R.id.txt_message));
                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_FILE_MESSAGE: {
                        TextView tv;
                        ImageView iv;
                        View v;

                        convertView = mInflater.inflate(com.sendbird.android.sample.R.layout.sendbird_view_group_file_message, parent, false);

                        v = convertView.findViewById(com.sendbird.android.sample.R.id.left_container);
                        viewHolder.setView("left_container", v);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_left_thumbnail);
                        viewHolder.setView("left_thumbnail", iv);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_left);
                        viewHolder.setView("left_image", iv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_left_name);
                        viewHolder.setView("left_name", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_left_time);
                        viewHolder.setView("left_time", tv);

                        v = convertView.findViewById(com.sendbird.android.sample.R.id.right_container);
                        viewHolder.setView("right_container", v);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_right_thumbnail);
                        viewHolder.setView("right_thumbnail", iv);
                        iv = (ImageView) convertView.findViewById(com.sendbird.android.sample.R.id.img_right);
                        viewHolder.setView("right_image", iv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_name);
                        viewHolder.setView("right_name", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_time);
                        viewHolder.setView("right_time", tv);
                        tv = (TextView) convertView.findViewById(com.sendbird.android.sample.R.id.txt_right_status);
                        viewHolder.setView("right_status", tv);

                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_TYPING_INDICATOR: {
                        convertView = mInflater.inflate(com.sendbird.android.sample.R.layout.sendbird_view_group_typing_indicator, parent, false);
                        viewHolder.setView("message", convertView.findViewById(com.sendbird.android.sample.R.id.txt_message));
                        convertView.setTag(viewHolder);
                        break;
                    }
                }
            }

            viewHolder = (LiveChatActivity.SendBirdMessagingAdapter.ViewHolder) convertView.getTag();
            switch (getItemViewType(position)) {
                case TYPE_UNSUPPORTED:
                    break;
                case TYPE_USER_MESSAGE:
                    UserMessage message = (UserMessage) item;
                    if (message.getSender().getUserId().equals(SendBird.getCurrentUser().getUserId())) {
                        viewHolder.getView("left_container", View.class).setVisibility(View.GONE);
                        viewHolder.getView("right_container", View.class).setVisibility(View.VISIBLE);

                        FileDownloadHelper.displayUrlImage(viewHolder.getView("right_thumbnail", ImageView.class), message.getSender().getProfileUrl(), true);
                        viewHolder.getView("right_name", TextView.class).setText(message.getSender().getNickname());
                        viewHolder.getView("right_message", TextView.class).setText(message.getMessage());
                        viewHolder.getView("right_time", TextView.class).setText(ChatInfoHelper.getDisplayDateTime(mContext, message.getCreatedAt()));

                        int unreadCount = mGroupChannel.getReadReceipt(message);
                        if (unreadCount > 1) {
                            viewHolder.getView("right_status", TextView.class).setText("Unread " + unreadCount);
                        } else if (unreadCount == 1) {
                            viewHolder.getView("right_status", TextView.class).setText("Unread");
                        } else {
                            viewHolder.getView("right_status", TextView.class).setText("");
                        }

                    } else {
                        viewHolder.getView("left_container", View.class).setVisibility(View.VISIBLE);
                        viewHolder.getView("right_container", View.class).setVisibility(View.GONE);

                        FileDownloadHelper.displayUrlImage(viewHolder.getView("left_thumbnail", ImageView.class), message.getSender().getProfileUrl(), true);
                        viewHolder.getView("left_name", TextView.class).setText(message.getSender().getNickname());
                        viewHolder.getView("left_message", TextView.class).setText(message.getMessage());
                        viewHolder.getView("left_time", TextView.class).setText(ChatInfoHelper.getDisplayDateTime(mContext, message.getCreatedAt()));
                    }
                    break;
                case TYPE_ADMIN_MESSAGE:
                    AdminMessage adminMessage = (AdminMessage) item;
                    viewHolder.getView("message", TextView.class).setText(Html.fromHtml(adminMessage.getMessage()));
                    break;
                case TYPE_FILE_MESSAGE:
                    final FileMessage fileLink = (FileMessage) item;

                    if (fileLink.getSender().getUserId().equals(SendBird.getCurrentUser().getUserId())) {
                        viewHolder.getView("left_container", View.class).setVisibility(View.GONE);
                        viewHolder.getView("right_container", View.class).setVisibility(View.VISIBLE);

                        FileDownloadHelper.displayUrlImage(viewHolder.getView("right_thumbnail", ImageView.class), fileLink.getSender().getProfileUrl(), true);
                        viewHolder.getView("right_name", TextView.class).setText(fileLink.getSender().getNickname());
                        if (fileLink.getType().toLowerCase().startsWith("image")) {
                            FileDownloadHelper.displayUrlImage(viewHolder.getView("right_image", ImageView.class), fileLink.getUrl());
                        } else {
                            viewHolder.getView("right_image", ImageView.class).setImageResource(com.sendbird.android.sample.R.drawable.sendbird_icon_file);
                        }
                        viewHolder.getView("right_time", TextView.class).setText(ChatInfoHelper.getDisplayDateTime(mContext, fileLink.getCreatedAt()));

                        int unreadCount = mGroupChannel.getReadReceipt(fileLink);
                        if (unreadCount > 1) {
                            viewHolder.getView("right_status", TextView.class).setText("Unread " + unreadCount);
                        } else if (unreadCount == 1) {
                            viewHolder.getView("right_status", TextView.class).setText("Unread");
                        } else {
                            viewHolder.getView("right_status", TextView.class).setText("");
                        }

                        viewHolder.getView("right_container").setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(mContext)
                                        .setTitle("SendBird")
                                        .setMessage("Do you want to download this file?")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    FileDownloadHelper.downloadUrl(fileLink.getUrl(), fileLink.getName(), mContext);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .create()
                                        .show();
                            }
                        });
                    } else {
                        viewHolder.getView("left_container", View.class).setVisibility(View.VISIBLE);
                        viewHolder.getView("right_container", View.class).setVisibility(View.GONE);

                        FileDownloadHelper.displayUrlImage(viewHolder.getView("left_thumbnail", ImageView.class), fileLink.getSender().getProfileUrl(), true);
                        viewHolder.getView("left_name", TextView.class).setText(fileLink.getSender().getNickname());
                        if (fileLink.getType().toLowerCase().startsWith("image")) {
                            FileDownloadHelper.displayUrlImage(viewHolder.getView("left_image", ImageView.class), fileLink.getUrl());
                        } else {
                            viewHolder.getView("left_image", ImageView.class).setImageResource(com.sendbird.android.sample.R.drawable.sendbird_icon_file);
                        }
                        viewHolder.getView("left_time", TextView.class).setText(ChatInfoHelper.getDisplayDateTime(mContext, fileLink.getCreatedAt()));

                        viewHolder.getView("left_container").setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: BUG: Unable to add window -- token null is not for an application
                                new AlertDialog.Builder(mContext)
                                        .setTitle("SendBird")
                                        .setMessage("Do you want to download this file?")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    FileDownloadHelper.downloadUrl(fileLink.getUrl(), fileLink.getName(), mContext);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .create()
                                        .show();
                            }
                        });
                    }
                    break;

                case TYPE_TYPING_INDICATOR: {
                    int itemCount = ((List) item).size();
                    String typeMsg = ((List) item).get(0)
                            + ((itemCount > 1) ? " +" + (itemCount - 1) : "")
                            + ((itemCount > 1) ? " are " : " is ")
                            + "typing...";
                    viewHolder.getView("message", TextView.class).setText(typeMsg);
                    break;
                }
            }

            return convertView;
        }

        private class ViewHolder {
            private Hashtable<String, View> holder = new Hashtable<>();
            private int type;

            public int getViewType() {
                return this.type;
            }

            public void setViewType(int type) {
                this.type = type;
            }

            public void setView(String k, View v) {
                holder.put(k, v);
            }

            public View getView(String k) {
                return holder.get(k);
            }

            public <T> T getView(String k, Class<T> type) {
                return type.cast(getView(k));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showToastMessage("Storage permission granted.");
                } else {
                    showToastMessage("Storage permission denied.");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE && data != null && data.getData() != null) {
                upload(data.getData());
            }
        }
    }

    private void upload(Uri uri) {
        Hashtable<String, Object> info = MediaHelper.getFileInfo(this, uri);

        final String path = (String) info.get("path");
        final File file = new File(path);
        final String name = file.getName();
        final String mime = (String) info.get("mime");
        final int size = (Integer) info.get("size");

        if (path == null) {
            Toast.makeText(this, "Uploading file must be located in local storage.",
                    Toast.LENGTH_LONG)
                    .show();
        } else {
            showUploadProgress(true);

            mGroupChannel.sendFileMessage(file, name, mime, size, "", new BaseChannel.SendFileMessageHandler() {
                @Override
                public void onSent(FileMessage fileMessage, SendBirdException e) {
                    showUploadProgress(false);
                    if (e != null) {
                        Toast.makeText(getApplicationContext(), "" + e.getCode() + ":" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAdapter.appendMessage(fileMessage);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!mIsUploading) {
            SendBird.removeChannelHandler(identifier);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsUploading) {
            SendBird.addChannelHandler(identifier, new SendBird.ChannelHandler() {
                @Override
                public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                    if (baseChannel.getUrl().equals(mGroupChannelUrl)) {
                        if (mAdapter != null) {
                            mGroupChannel.markAsRead();
                            mAdapter.appendMessage(baseMessage);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onReadReceiptUpdated(GroupChannel groupChannel) {
                    if (groupChannel.getUrl().equals(mGroupChannelUrl)) {
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onTypingStatusUpdated(GroupChannel groupChannel) {
                    if (groupChannel.getUrl().equals(mGroupChannelUrl)) {
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onUserJoined(GroupChannel groupChannel, User user) {
                    if (groupChannel.getUrl().equals(mGroupChannelUrl)) {
//                        updateGroupChannelTitle();
                    }
                }

                @Override
                public void onUserLeft(GroupChannel groupChannel, User user) {
                    if (groupChannel.getUrl().equals(mGroupChannelUrl)) {
//                        updateGroupChannelTitle();
                    }
                }
            });

//            initGroupChannel();
        } else {
            mIsUploading = false;

            /**
             * Set this as true to restart auto-background detection,
             * when your Activity is shown again after the external Activity is finished.
             */
            SendBird.setAutoBackgroundDetection(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }
}