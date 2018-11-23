package hub.constructor.constructorhub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import hub.constructor.constructorhub.Class.Chat;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Class.Upload;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;

    FirebaseUser current_user;



    public MessageAdapter(Context mContext, List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_right,viewGroup,false);
            return new MessageAdapterViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_left,viewGroup,false);
            return new MessageAdapterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.textView.setText(chat.getMessage());



    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;


        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.showMessageId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        current_user = FirebaseAuth.getInstance().getCurrentUser();

        if (mChat.get(position).getSender().equals(current_user.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }
}
