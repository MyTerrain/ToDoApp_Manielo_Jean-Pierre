package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//responsible for taking the data at a particular position and putting it into a view holder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> Items, OnLongClickListener longClickListener ) {
       this.items = Items;
       this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use layout inflator to inflate a view
      View toDoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //Wrap it inside a view holder and return it
        return new ViewHolder(toDoView);

    }

    //Responsible for binding data to a particular view Holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //grab item at position
    String item = items.get(position);
    //Bind item into specified view holder
    holder.bind(item);
    }

    //Tell the recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItems = itemView.findViewById(android.R.id.text1);

        }
        //Update view inside of view holder with this data
        public void bind(String items) {
            tvItems.setText(items);
            tvItems.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
        }
    }

}
