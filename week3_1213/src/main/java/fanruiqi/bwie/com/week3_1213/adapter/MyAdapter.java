package fanruiqi.bwie.com.week3_1213.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import fanruiqi.bwie.com.week3_1213.R;
import fanruiqi.bwie.com.week3_1213.bean.ResponseBean;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;

    private List<ResponseBean.DataBean> list;
    public MyAdapter(Context context, List<ResponseBean.DataBean> data) {
        mContext = context;
        list=data;
    }

    public void remove(int position1) {
        list.remove(position1);
        notifyItemRemoved(position1);
        notifyDataSetChanged();
    }

    public static final int ITEM_ONE=0;
    public static final int ITEM_TWO=1;
    public static final int ITEM_THREE=2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_ONE){
            View view = View.inflate(parent.getContext(), R.layout.item_onepicture, null);
            ViewHodler1 viewHodler1 = new ViewHodler1(view);
            return viewHodler1;
        }else if (viewType==ITEM_TWO){
            View view = View.inflate(parent.getContext(), R.layout.item_twopicture, null);
            ViewHodler2 viewHodler2 = new ViewHodler2(view);
            return viewHodler2;
        }else if (viewType==ITEM_THREE){
            View view = View.inflate(parent.getContext(), R.layout.item_threepicture, null);
            ViewHodler3 viewHodler3 = new ViewHodler3(view);
            return viewHodler3;
        }
        return null;
    }



    public interface OnLongClickListener{
        void OnLongClick(int position1);
        void OnClick(ImageView imageView);
    }

    OnLongClickListener mOnLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener){
        mOnLongClickListener=onLongClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        int itemViewType = holder.getItemViewType();
        if (itemViewType==ITEM_ONE){
            final ViewHodler1 viewHodler1 = (ViewHodler1) holder;
            viewHodler1.mTextView.setText(list.get(position).getTitle());
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s()).into(viewHodler1.mImageView);

            ((ViewHodler1) holder).mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnLongClickListener.OnClick(viewHodler1.mImageView);
                }
            });
        }else if (itemViewType==ITEM_TWO){
            ViewHodler2 viewHodler2 = (ViewHodler2) holder;
            viewHodler2.mTextView.setText(list.get(position).getTitle());
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s()).into(viewHodler2.mImageView);
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s02()).into(viewHodler2.mImageView02);
        }else if (itemViewType==ITEM_THREE){
            ViewHodler3 viewHodler3 = (ViewHodler3) holder;
            viewHodler3.mTextView.setText(list.get(position).getTitle());
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s()).into(viewHodler3.mImageView);
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s02()).into(viewHodler3.mImageView02);
            Glide.with(mContext).load(list.get(position).getThumbnail_pic_s03()).into(viewHodler3.mImageView03);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                int position1 = holder.getLayoutPosition();
                mOnLongClickListener.OnLongClick(position1);
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {

        String pic_s02 = list.get(position).getThumbnail_pic_s02();
        String pic_s03 = list.get(position).getThumbnail_pic_s03();

        if (pic_s02==null&&pic_s03==null){
            return ITEM_ONE;
        }else if (pic_s02!=null&&pic_s03==null){
            return ITEM_TWO;
        }else {
            return ITEM_THREE;
        }
    }

    public class ViewHodler1 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        public ViewHodler1(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.item_text);
            mImageView = itemView.findViewById(R.id.item_image);
        }
    }

    public class ViewHodler2 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        ImageView mImageView02;
        public ViewHodler2(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_text);
            mImageView = itemView.findViewById(R.id.item_image01);
            mImageView02 = itemView.findViewById(R.id.item_image02);
        }
    }

    public class ViewHodler3 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        ImageView mImageView02;
        ImageView mImageView03;
        public ViewHodler3(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_text);
            mImageView = itemView.findViewById(R.id.item_image01);
            mImageView02 = itemView.findViewById(R.id.item_image02);
            mImageView03 = itemView.findViewById(R.id.item_image03);
        }
    }
}
