package com.example.kidroca.mylittlequizapp.main.navigation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kidroca.mylittlequizapp.R;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class AdapterNavigation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<NavLink> links = Collections.emptyList();
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;
    private LayoutInflater inflater;
    private Context context;

    public AdapterNavigation(Context context, List<NavLink> links) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.links = links;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.navigation_drawer_header, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        } else {
            View view = inflater.inflate(R.layout.navigation_drawer_item, parent, false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            NavLink current = links.get(position - 1);
            itemHolder.title.setText(current.title);
            itemHolder.icon.setImageResource(current.iconId);
        }
    }

    @Override
    public int getItemCount() {
        return links.size() + 1;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.navText) TextView title;
        @InjectView(R.id.navIcon) ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
//            title= (TextView) itemView.findViewById(R.id.navText);
//            icon= (ImageView) itemView.findViewById(R.id.navIcon);
        }
    }
}
