package com.example.omd.my3qaqer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Delta on 21/06/2017.
 */

public class Recyclerview_Adapter extends RecyclerView.Adapter<Recyclerview_Adapter.ViewHolder>{

    List<Drug_Model> DrugList;
    LayoutInflater inflater;
    Context mContext;
    FirebaseAuth mAuth;
    DatabaseReference dref;
    AlertDialog.Builder mAlertDialog;

    public Recyclerview_Adapter(List<Drug_Model> drugList, Context mContext)
    {
        DrugList      = drugList;
        this.mContext = mContext;
        inflater      = LayoutInflater.from(mContext);
        mAuth         = FirebaseAuth.getInstance();
        dref          = FirebaseDatabase.getInstance().getReference();
        mAlertDialog  = new AlertDialog.Builder(mContext);
        mAlertDialog.setMessage("هل تريد حزف الدواء ؟");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.drug_row,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Drug_Model drug_model = DrugList.get(position);
        Picasso.with(mContext).load(drug_model.getDrug_image()).into(holder.drug_image);
        holder.drug_name.setText(drug_model.getDrug_name());
        holder.drug_concentrate.setText(drug_model.getDrug_concentration());
        holder.drugtype.setText(drug_model.getDrug_type());
        holder.delete_drug_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drug_Model model = DrugList.get(holder.getLayoutPosition());
                String id        = model.getDrug_name()+"_"+model.getDrug_concentration()+"_"+model.getDrug_type();
                Delete_Drug(mAuth.getCurrentUser().getUid().toString(),id);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return DrugList.size();
    }
    private void Delete_Drug(final String pharmacy_id, final String drug_id)
    {
        mAlertDialog.setPositiveButton("حزف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference DruginfoRef = dref.child(Firebase_DataBase_Holder.drugs_Info).child(pharmacy_id);
                DruginfoRef.child(drug_id).getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(mContext,mContext.getResources().getString(R.string.deleteDrug_txt), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mAlertDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mContext,"الغاء", Toast.LENGTH_SHORT).show();
            }
        });
        mAlertDialog.create();
        mAlertDialog.show();

    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView delete_drug_image;
        ImageView drug_image;
        TextView drug_name,drug_concentrate,drugtype;
        public ViewHolder(View itemView) {
            super(itemView);
            delete_drug_image = (ImageView) itemView.findViewById(R.id.delete_drug_image);
            drug_image        = (ImageView) itemView.findViewById(R.id.drug_image);
            drug_name         = (TextView) itemView.findViewById(R.id.drug_name);
            drug_concentrate  = (TextView) itemView.findViewById(R.id.drug_concentrate);
            drugtype          = (TextView) itemView.findViewById(R.id.drug_type);
        }
    }
}
