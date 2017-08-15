package com.skripsigg.heyow.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MessageChatModel;
import com.skripsigg.heyow.utils.others.Constants;

import java.util.List;

/**
 * Created by Aldyaz on 12/24/2016.
 */

public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    
    private List<MessageChatModel> messageChatList;

    public MessageChatAdapter(List<MessageChatModel> messageChatList) {
        this.messageChatList = messageChatList;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageChatList.get(position).getSenderOrRecipientStatus() == Constants.SENDER) {
            Log.e(TAG, "Sender");
            return Constants.SENDER;
        } else {
            Log.e(TAG, "Recipient");
            return Constants.RECIPIENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder recyclerHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Constants.SENDER:
                View senderView = inflater.inflate(R.layout.chat_sender_message_layout, parent, false);
                recyclerHolder = new SenderViewHolder(senderView);
                break;
            case Constants.RECIPIENT:
                View recipientView = inflater.inflate(R.layout.chat_recipient_message_layout, parent, false);
                recyclerHolder = new RecipientViewHolder(recipientView);
                break;
            default:
                View defaultSenderView = inflater.inflate(R.layout.chat_sender_message_layout, parent, false);
                recyclerHolder = new SenderViewHolder(defaultSenderView);
                break;
        }

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case Constants.SENDER:
                SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
                configureSenderView(senderViewHolder, position);
                break;
            case Constants.RECIPIENT:
                RecipientViewHolder recipientViewHolder = (RecipientViewHolder) holder;
                configureRecipientView(recipientViewHolder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageChatList.size();
    }
    
    public void refillAdapter(MessageChatModel newMessageChat) {
        // Add new message chat to list.
        messageChatList.add(newMessageChat);

        // Refresh view.
        notifyItemInserted(getItemCount() - 1);
    }
    
    public void refillFirstTimeAdapter(List<MessageChatModel> newMessageChatList) {
        // Add new message chat to list.
        messageChatList.clear();
        messageChatList.addAll(newMessageChatList);

        // Refresh view.
        notifyItemInserted(getItemCount() - 1);
    }
    
    private void configureSenderView(SenderViewHolder senderViewHolder, int position) {
        MessageChatModel senderMessage = messageChatList.get(position);
        senderViewHolder.getSenderMessageContentText().setText(senderMessage.getMessageContent());
        senderViewHolder.getSenderMessageTimeText().setText(senderMessage.getMessageTime());
    }
    
    private void configureRecipientView(RecipientViewHolder recipientViewHolder, int position) {
        MessageChatModel recipientMessage = messageChatList.get(position);
        recipientViewHolder.getRecipientMessageUserName().setText(recipientMessage.getUserName());
        recipientViewHolder.getRecipientMessageContentText().setText(recipientMessage.getMessageContent());
        recipientViewHolder.getRecipientMessageTimeText().setText(recipientMessage.getMessageTime());
    }

    public void cleanUp() {
        messageChatList.clear();
    }

    /** ### Sender and Recipient ViewHolder class ### **/

    /**
     * Sender ViewHolder class.
     */
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        private TextView senderMessageContentText, senderMessageTimeText;

        public SenderViewHolder(View itemView) {
            super(itemView);
            senderMessageContentText = (TextView) itemView.findViewById(R.id.sender_message_content_text);
            senderMessageTimeText = (TextView) itemView.findViewById(R.id.sender_message_time_text);
        }

        public TextView getSenderMessageContentText() {
            return senderMessageContentText;
        }

        public void setSenderMessageContentText(TextView senderMessageContentText) {
            this.senderMessageContentText = senderMessageContentText;
        }

        public TextView getSenderMessageTimeText() {
            return senderMessageTimeText;
        }

        public void setSenderMessageTimeText(TextView senderMessageTimeText) {
            this.senderMessageTimeText = senderMessageTimeText;
        }
    }

    /**
     * Sender ViewHolder class.
     */
    public class RecipientViewHolder extends RecyclerView.ViewHolder {
        private TextView recipientMessageUserName,
                recipientMessageContentText,
                recipientMessageTimeText;

        public RecipientViewHolder(View itemView) {
            super(itemView);
            recipientMessageUserName = (TextView) itemView.findViewById(R.id.recipient_message_user_name);
            recipientMessageContentText = (TextView) itemView.findViewById(R.id.recipient_message_content_text);
            recipientMessageTimeText = (TextView) itemView.findViewById(R.id.recipient_message_time_text);
        }

        public TextView getRecipientMessageUserName() {
            return recipientMessageUserName;
        }

        public void setRecipientMessageUserName(TextView recipientMessageUserName) {
            this.recipientMessageUserName = recipientMessageUserName;
        }

        public TextView getRecipientMessageContentText() {
            return recipientMessageContentText;
        }

        public void setRecipientMessageContentText(TextView recipientMessageContentText) {
            this.recipientMessageContentText = recipientMessageContentText;
        }

        public TextView getRecipientMessageTimeText() {
            return recipientMessageTimeText;
        }

        public void setRecipientMessageTimeText(TextView recipientMessageTimeText) {
            this.recipientMessageTimeText = recipientMessageTimeText;
        }
    }
}
