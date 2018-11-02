package hub.constructor.constructorhub.seller;

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

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Upload;

public class PostProductAdapter extends RecyclerView.Adapter<PostProductAdapter.PostViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public PostProductAdapter(Context context, List<Upload> uploads){

        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.posted_product,viewGroup,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Upload uploadCurrent = mUploads.get(position);
        String imageUrl = uploadCurrent.getmImageUrl();
        String heading = uploadCurrent.getHeading();
        String companyName = uploadCurrent.getCompanyName();
        String address = uploadCurrent.getCompanyAddress();
        String service = uploadCurrent.getService();
        String price = uploadCurrent.getPrice();
        String description = uploadCurrent.getDescription();

        holder.textView1.setText(heading);
        holder.textView2.setText(service);
        holder.textView3.setText("Price: "+price+"Tk");

        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop().into(holder.imageView1);


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public ImageView imageView1;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.postHeadingId);
            textView2 = itemView.findViewById(R.id.postServiceId);
            textView3 = itemView.findViewById(R.id.postPriceId);
            imageView1 = itemView.findViewById(R.id.postImageId);

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
