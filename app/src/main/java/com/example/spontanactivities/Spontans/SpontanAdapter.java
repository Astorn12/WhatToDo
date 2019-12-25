package com.example.spontanactivities.Spontans;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;

import java.util.List;

public class SpontanAdapter extends RecyclerView.Adapter<SpontanAdapter.MyViewHolder> {
    private List<Spontan> activities;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        public ImageView image;
        public TextView name;
        public LinearLayout tags;
        public MyViewHolder(LinearLayout parent) {
            super(parent);

            this.image= new ImageView(parent.getContext());
            parent.addView(image);

            this.name= new TextView(parent.getContext());
            parent.addView(name);
            name.setTextSize(25);

            this.tags= new LinearLayout(parent.getContext());
            parent.addView(tags);
            this.tags.setOrientation(LinearLayout.HORIZONTAL);
            tags.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SpontanAdapter(List<Spontan> activities) {
        this.activities=activities;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SpontanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        LinearLayout linearLayout= new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(30,10,10,10);

        MyViewHolder vh = new MyViewHolder(linearLayout);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final Spontan spontan= this.activities.get(position);
       // holder.image.setImageResource(spontan.getResource());

        System.out.println("HOLDERUJE");

        holder.name.setText(spontan.getName());
        holder.tags.removeAllViews();
        for(Tag tag: spontan.getTags()){
            ImageView imageView=tag.getIcon(holder.itemView.getContext());


            holder.tags.addView(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            imageView.setAdjustViewBounds(true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(holder.itemView.getContext(), SpontanDetails.class);
                intent.putExtra("id", spontan.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.activities.size();
    }

    public void updateSpontanList(List<Spontan> spontans){
        this.activities=spontans;
    }

    public List<Spontan> getActiveSpontans(){
        return this.activities;
    }

}
