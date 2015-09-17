package com.mopel.databindingdemo.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mopel.databindingdemo.R;
import com.mopel.databindingdemo.databinding.ItemBabyBinding;
import com.mopel.databindingdemo.model.Baby;
import com.mopel.databindingdemo.viewModel.BabyViewModel;

import java.util.List;

/**
 * Author: yanhao(amosbake@gmail.com)
 * Date : 2015-09-17
 * Time: 16:30
 */
public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.BabyHolder> {
    private static final String TAG = "BabyAdapter";
    private List<Baby> mBabies;
    private Context mContext;

    public BabyAdapter(Context context) {
        mContext = context;
    }

    public void setItems(List<Baby> babies){
        mBabies=babies;
        notifyDataSetChanged();
    }


    @Override
    public BabyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_baby,parent,false));
    }

    @Override
    public void onBindViewHolder(BabyHolder holder, int position) {
        ItemBabyBinding babyBinding=DataBindingUtil.bind(holder.itemView);
        babyBinding.setViewModel(new BabyViewModel(mContext,mBabies.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBabies.size();
    }

    public static class BabyHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public BabyHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
