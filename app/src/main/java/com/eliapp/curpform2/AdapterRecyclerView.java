package com.eliapp.curpform2;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.eliapp.curpform2.models.Curp;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolderData> {

    private ArrayList<Curp> CurpList;

    public TextView Name, FatherLastName, MotherLastName, State, Gender, Birthday, Curp;
    public ImageView Image;

    public class ViewHolderData extends RecyclerView.ViewHolder {

        public ViewHolderData(@NonNull View itemView){
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            FatherLastName = itemView.findViewById(R.id.fatherLastName);
            MotherLastName = itemView.findViewById(R.id.motherLastName);
            State = itemView.findViewById(R.id.state);
            Gender = itemView.findViewById(R.id.gender);
            Birthday = itemView.findViewById(R.id.birthday);
            Curp = itemView.findViewById(R.id.curp_title);
            Image = itemView.findViewById(R.id.see_image);
        }
    }

    public AdapterRecyclerView(ArrayList<Curp> curpList) {this.CurpList = curpList; }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.curp_list, viewGroup, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolderData viewHolderData, int i) {
        Curp c = CurpList.get(i);

        Name.setText(c.getName());
        FatherLastName.setText(c.getFatherLastName());
        MotherLastName.setText(c.getMotherLastName());
        State.setText(c.getState());
        Gender.setText(c.getGender());
        Birthday.setText(c.getBirthdayString());
        Curp.setText(c.getCurp());

        Uri uri = Uri.parse(c.getImgpath());
        Image.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        try{
            return CurpList.size();
        } catch (Exception e){
            return 0;
        }
    }
}