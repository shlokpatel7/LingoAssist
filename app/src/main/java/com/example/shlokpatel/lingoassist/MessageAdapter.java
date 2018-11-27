package com.example.shlokpatel.lingoassist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<MyText> mMessageList;

    public MessageAdapter(Context mContext, List<MyText> mMessageList) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        MyText myText = (MyText) mMessageList.get(position);

        if (myText.getViewType()==(VIEW_TYPE_MESSAGE_SENT)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_right, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_left, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyText myText = (MyText) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(myText);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(myText);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messageView;

        public SentMessageHolder(View itemView) {
            super(itemView);
            messageView= (TextView) itemView.findViewById(R.id.message_view_right);
        }

        void bind(MyText message) {
            messageView.setText(message.getMessage());
        }
    }

    class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView messageView;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageView= (TextView) itemView.findViewById(R.id.message_view_left);
        }

        void bind(MyText message) {
            messageView.setText(message.getMessage());
        }
    }

}
