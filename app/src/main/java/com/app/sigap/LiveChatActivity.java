package com.app.sigap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.master.MainMenuActivity;
import com.app.sources.ChatIDE;
import com.app.sources.Store_Pref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.MessageListQuery;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import java.util.List;

import static android.provider.UserDictionary.Words.APP_ID;

public class LiveChatActivity extends AppCompatActivity {

    /**
     * UI Reference
     * */
    private Button btn_send;
    private EditText etxt_message;
    private ImageButton btn_close;
    private TextView txt_channel_name;

    public static String sUserId;
    private String mNickname;
    Store_Pref pref;
    private BaseChannel openChannel;

    /**
     * End of UI Reference
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.sendbird_fragment_group_chat);

        /**
         * Fadhli listener
         * */
        setFont();

        Exit();

        Initializing_with_APP_ID();

        StoreUser();

        Connecting_with_UserID();

        Entering_an_Open_Channel();

        OpenChannel();

        Sending_Messages();

        Receiving_Messages();

        Deleting_Messages();

        Updating_User_Profile();

        Loading_Previous_Messages();

        Connecting_with_UserID_and_Access_Token();

        Exiting_from_an_Open_Channel();
        /**
         * End of Fadhli listener
         * */
    }

    /**
     * Fadhli listener
     * */
    private void Connecting_with_UserID ()
    {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }
            }
        });

    }

    private void Connecting_with_UserID_and_Access_Token ()
    {
        SendBird.connect(sUserId, ChatIDE.API_TOKEN, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }
            }
        });

    }

    private void Deleting_Messages ()
    {
        Object BASE_MESSAGE;
        BASE_MESSAGE = new Object();

        BaseChannel channel;
        channel = new BaseChannel(null) {
            @Override
            public void deleteMessage(BaseMessage message, DeleteMessageHandler handler) {
                super.deleteMessage(message, handler);
            }
        };
        /*
        channel.deleteMessage(BASE_MESSAGE, new BaseChannel.DeleteMessageHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }
            }
        });
        */
    }

    private void Entering_an_Open_Channel ()
    {
        OpenChannel.getChannel(ChatIDE.CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });
            }
        });

    }

    private void Exiting_from_an_Open_Channel ()
    {
        OpenChannel.getChannel(ChatIDE.CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }

                openChannel.exit(new OpenChannel.OpenChannelExitHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });
            }
        });

    }

    private void Exit ()
    {
        btn_close = (ImageButton) findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of live chat activity
                 * */
                finishAffinity();

                /**
                 * Back to dashboard
                 * */
                Intent intent = new Intent(LiveChatActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Initializing_with_APP_ID ()
    {
        SendBird.init(ChatIDE.API_ID, this);
    }

    /*
    private void Loading_Messages_by_Timestamp ()
    {
        MessageListQuery mMessageListQuery = openChannel.createMessageListQuery();
        long EARLIEST_MESSAGE_TIMESTAMP, LIMIT, REVERSE_ORDER;
        EARLIEST_MESSAGE_TIMESTAMP = 0;
        LIMIT = 100;
        REVERSE_ORDER = 1000;
        mMessageListQuery.prev(EARLIEST_MESSAGE_TIMESTAMP, LIMIT, REVERSE_ORDER, new MessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> list, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }
            }
        });

    }
    */

    private void Loading_Previous_Messages ()
    {
        PreviousMessageListQuery mPrevMessageListQuery = openChannel.createPreviousMessageListQuery();
        mPrevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> messages, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }
            }
        });

    }

    private void OpenChannel ()
    {
        OpenChannel.getChannel(ChatIDE.CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });
            }
        });
    }

    private void Receiving_Messages ()
    {
        String UNIQUE_HANDLER_ID = sUserId;

        SendBird.addChannelHandler(UNIQUE_HANDLER_ID, new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
            }
        });

    }

    private void Sending_Messages ()
    {
        etxt_message = (EditText) findViewById(R.id.etxt_message);

        final String MESSAGE = etxt_message.getText().toString();
        final String DATA = "";

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChannel.sendUserMessage(MESSAGE, DATA, new BaseChannel.SendUserMessageHandler() {
                    @Override
                    public void onSent(UserMessage userMessage, SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });
            }
        });
    }

    @SuppressWarnings("")
    private void setFont ()
    {
        txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);
        etxt_message = (EditText) findViewById(R.id.etxt_message);
        btn_send = (Button) findViewById(R.id.btn_send);

        /**
         * Set typeface
         * */
        Typeface typeface;
        typeface = Typeface.createFromAsset(
            getApplicationContext().getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );

        txt_channel_name.setTypeface(typeface);
        etxt_message.setTypeface(typeface);
        btn_send.setTypeface(typeface);
    }

    private void StoreUser ()
    {
        pref = new Store_Pref(this);

        sUserId = pref.get_member_id();
        mNickname = pref.get_member_name();
    }

    private void Updating_User_Profile ()
    {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }

                SendBird.updateCurrentUserInfo(mNickname, "PROFILE_URL", new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });
            }
        });

    }

    /**
     * End of Fadhli listener
     * */

}
