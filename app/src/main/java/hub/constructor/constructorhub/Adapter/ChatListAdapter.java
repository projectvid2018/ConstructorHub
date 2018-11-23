package hub.constructor.constructorhub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hub.constructor.constructorhub.Class.Upload;
import hub.constructor.constructorhub.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListAdapterViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private ChatListAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ChatListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public ChatListAdapter(Context context, List<Upload> uploads) {

        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public ChatListAdapter.ChatListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_recycler_layout, viewGroup, false);
        return new ChatListAdapter.ChatListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatListAdapterViewHolder holder, int position) {

        Upload uploadCurrent = mUploads.get(position);
        String imageUrl = uploadCurrent.getmImageUrl();
        String userName = uploadCurrent.getUserName();
        String heading = uploadCurrent.getHeading();
        String price = uploadCurrent.getPrice();

        holder.textView1.setText(userName);
        holder.textView2.setText(""+heading);
        holder.textView3.setText("Price: " + price + " Tk");

        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ChatListAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;
        private ImageView imageView;

        public ChatListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.chatListPersonNameId);
            textView2 = itemView.findViewById(R.id.chatListHeadingId);
            textView3 = itemView.findViewById(R.id.chatListPriceId);
            imageView = itemView.findViewById(R.id.chatListImageId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
