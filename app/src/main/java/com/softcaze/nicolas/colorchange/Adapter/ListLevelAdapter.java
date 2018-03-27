package com.softcaze.nicolas.colorchange.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 14/02/2018.
 */

public class ListLevelAdapter extends ArrayAdapter<Level> {
    public ListLevelAdapter(Context context, List<Level> level) {
        super(context, 0, level);
    }

    static class ViewHolder{
        TextView name_lvl;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        List<ImageView> img;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Level myLevel = getItem(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_level, parent, false);

            holder = new ViewHolder();

            holder.name_lvl = (TextView) convertView.findViewById(R.id.name_lvl);

            holder.star1 = (ImageView) convertView.findViewById(R.id.star1);
            holder.star2 = (ImageView) convertView.findViewById(R.id.star2);
            holder.star3 = (ImageView) convertView.findViewById(R.id.star3);

            holder.img = new ArrayList<ImageView>();

            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_1));
            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_2));
            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_3));
            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_4));
            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_5));
            holder.img.add((ImageView) convertView.findViewById(R.id.circle_color_6));

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();

            holder.name_lvl.setText("");
            holder.star1.setImageDrawable(null);
            holder.star2.setImageDrawable(null);
            holder.star3.setImageDrawable(null);

            for (ImageView i: holder.img) {
                i.setImageDrawable(null);
            }
        }


        for(int i = 0; i < holder.img.size(); i++){
            if(myLevel.getCouleurs().size() == i){
                break;
            }
            if(myLevel.getCouleurs().get(i) != null){
                holder.img.get(i).setImageResource(R.drawable.circle_shape);
                GradientDrawable bgShape = (GradientDrawable) holder.img.get(i).getDrawable();
                bgShape.setColor(getContext().getResources().getColor(myLevel.getCouleurs().get(i)));
            }
        }

        // Populate the data into the template view using the data object
        holder.name_lvl.setText(getContext().getResources().getString(R.string.libelle_lvl) + " " + myLevel.getNum());

        for(int i = 0; i < holder.img.size(); i++){
            if(myLevel.getCouleurs().size() == i){
                break;
            }
            if(myLevel.getCouleurs().get(i) != null){
                holder.img.get(i).setImageResource(R.drawable.circle_shape);
                GradientDrawable bgShape = (GradientDrawable) holder.img.get(i).getDrawable();
                bgShape.setColor(getContext().getResources().getColor(myLevel.getCouleurs().get(i)));
            }
        }

        switch(myLevel.getNbrStars()){
            case 0:
                holder.star1.setImageResource(R.drawable.star_white);
                holder.star2.setImageResource(R.drawable.star_white);
                holder.star3.setImageResource(R.drawable.star_white);
                break;
            case 1:
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star_white);
                holder.star3.setImageResource(R.drawable.star_white);
                break;
            case 2:
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star_white);
                break;
            case 3:
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                break;
            default:
                holder.star1.setImageResource(R.drawable.star_white);
                holder.star2.setImageResource(R.drawable.star_white);
                holder.star3.setImageResource(R.drawable.star_white);
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
