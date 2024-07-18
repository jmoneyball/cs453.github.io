package edu.csueb.android.zooDirectory_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    //declarations
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<AnimalDetails> dir;

    //constructor
    public Adapter(RecyclerViewInterface recyclerViewInterface, Context context, ArrayList<AnimalDetails> dir) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.dir = dir;
    }

    //inflates view
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.viewName.setText(dir.get(position).getAnimalName());
        holder.viewImage.setImageResource(dir.get(position).getAnimalImage());
    }

    //returns size of view data
    @Override
    public int getItemCount() {
        return dir.size();
    }

    //populates
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView viewImage;
        TextView viewName, viewDescription;

        public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            viewName = (TextView) view.findViewById(R.id.animalName);
            viewImage = (ImageView) view.findViewById(R.id.animalImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

