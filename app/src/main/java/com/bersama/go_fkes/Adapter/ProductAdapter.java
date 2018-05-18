package com.bersama.go_fkes.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bersama.go_fkes.Activity.ListActivity;
import com.bersama.go_fkes.Model.Product;
import com.bersama.go_fkes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by user on 12/15/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private String noTelp;
    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        Picasso.with(mCtx)
                .load("https://diosatriani44.000webhostapp.com/map/images/foto/"+ product.getImage())
                .into(holder.iv_list);

        Log.d("tesss", product.getImage());
        holder.tv_namalist.setText(product.getNama());
        holder.tv_alamatlist.setText(product.getAlamat());
        holder.btn_notelp.setText(product.getNotelp());
        holder.btn_notelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListActivity) mCtx).functionToRun();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tv_namalist, tv_alamatlist;
        ImageView iv_list;
        Button btn_notelp;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tv_namalist = itemView.findViewById(R.id.tv_namalist);
            tv_alamatlist = itemView.findViewById(R.id.tv_alamatlist);
            iv_list = itemView.findViewById(R.id.iv_list);
            btn_notelp = itemView.findViewById(R.id.btn_notelp);
        }
    }
}
