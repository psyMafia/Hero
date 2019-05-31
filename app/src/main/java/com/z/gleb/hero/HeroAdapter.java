package com.z.gleb.hero;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {

    private Context _context;
    private ArrayList<HeroItem> _heroList;
    private OnItemClickListener _listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this._listener = listener;
    }



    public HeroAdapter(Context context, ArrayList<HeroItem> heroList) {
        _context = context;
        _heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(_context).inflate(R.layout.hero_item, viewGroup, false );
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder heroViewHolder, int i) {

        HeroItem currentItem = _heroList.get(i);
        String imageUrl = currentItem.getImageUrl();
        String heroName = currentItem.getTitle();
        ArrayList<String> heroAbilities = currentItem.getAbilities();



        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(50)
                .oval(false)
                .build();
        Picasso.get().load(imageUrl).fit().centerCrop().transform(transformation).into(heroViewHolder.heroImageView, new Callback() {
           @Override
           public void onSuccess() {

           }

           @Override
           public void onError(Exception e) {

           }
       });

       heroViewHolder.heroNameTextView.setText(heroName);

      // heroViewHolder.heroAbiitiesTextView.setText(heroAbilities.get(0));
        heroViewHolder.heroAbilitiesTextView.setText("");
        for (int k = 0; k < heroAbilities.size(); k++) {
            heroViewHolder.heroAbilitiesTextView.append(heroAbilities.get(k) + " ");
        }
        if (currentItem.isFavorite()) {
            heroViewHolder.favoritIcon.setVisibility(View.VISIBLE);
        }else {
            heroViewHolder.favoritIcon.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return _heroList.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder{

        public ImageView heroImageView;
        public TextView heroNameTextView;
        public TextView heroAbilitiesTextView;
        public TextView favoritIcon;

        public HeroViewHolder(@NonNull final View itemView) {
            super(itemView);

            heroImageView = itemView.findViewById(R.id.roundedImageView);
            heroNameTextView = itemView.findViewById(R.id.text_view_heroName);
            heroAbilitiesTextView = itemView.findViewById(R.id.text_view_heroAbilities);
            favoritIcon = itemView.findViewById(R.id.favorit_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            _listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }


}
