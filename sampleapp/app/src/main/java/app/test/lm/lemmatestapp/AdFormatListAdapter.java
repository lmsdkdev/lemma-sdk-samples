package app.test.lm.lemmatestapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class AdFormatListAdapter extends RecyclerView.Adapter<AdFormatListAdapter.AdViewHolder> {

    private final String TAG = "AdFormatListAdapter";
    private final List<LIST_ITEM> itemList;
    private OnItemClickListener itemClickListener;
    private Context mContext;
    AdFormatListAdapter(@NonNull Context context, @NonNull List<LIST_ITEM> list) {
        itemList = list;
        this.mContext = context;
    }

    void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ad_list_item, parent, false);
        return new AdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {
        String item = getItem(position).getDisplayText();
        holder.title.setText(item);
        int height = holder.itemView.getLayoutParams().height;
        if (getItem(position).getActivity() == null) {
            holder.itemView.setBackgroundColor(0xFFCACFD2);
            holder.title.setTextSize((5 * Resources.getSystem().getDisplayMetrics().density));
            height = (int) (40 * Resources.getSystem().getDisplayMetrics().density);
        } else {
            height = (int) (60 * Resources.getSystem().getDisplayMetrics().density);
            holder.title.setContentDescription(getItem(position).getActivity().getSimpleName());
        }
        holder.itemView.getLayoutParams().height = height;

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private LIST_ITEM getItem(int position) {
        return itemList.get(position);
    }

    public enum LIST_ITEM {
        //Add row details sequentially as  will be displayed on activity
        LIST_ITEM_HEADER(null, "Ad Formats"),
        LIST_ITEM_BANNER(BannerActivity.class, "Banner"),
        LIST_ITEM_INTERSTITIAL(InterstitialActivity.class, "Interstitial"),
        LIST_ITEM__VIDEO(VideoDebugActivity.class, "Video"),
        LIST_ITEM__VIDEO_INSTL(VideoInterstitialActivity.class, "Video Interstitial");



        private Class activity;
        private String displayText;

        LIST_ITEM(Class activity, String text) {
            this.activity = activity;
            this.displayText = text;
        }

        public Class getActivity() {
            return activity;
        }

        public String getDisplayText() {
            return displayText;
        }
    }


    interface OnItemClickListener {
        void onClick(View view, int position);
    }

    class AdViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        AdViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.ad_title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != itemClickListener) {
                        itemClickListener.onClick(view, getAdapterPosition());
                    }
                }
            });
        }

    }
}
