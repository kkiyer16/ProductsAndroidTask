package com.qstest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qstest.databinding.ProductsRecyclerViewLayoutBinding
import com.qstest.models.ProductsModel
import com.qstest.utils.ProductsHolder

class ProductsAdapter(var productsList: ArrayList<ProductsModel>, var context: Context) :
    RecyclerView.Adapter<ProductsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val binding = ProductsRecyclerViewLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductsHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        val products = productsList[position]
        holder.binding.tvProductTitle.text = products.productName
        holder.binding.tvProductDescription.text = products.productDesc
        if(products.productPrice == "null"){
            holder.binding.tvProductPriceDash.visibility = View.VISIBLE
        }else{
            holder.binding.tvProductPrice.text = products.productPrice
        }
        if(products.productImage != "null"){
            Glide.with(holder.binding.root).load(products.productImage).into(holder.binding.productImage)
        }
    }

    fun update(productsList: ArrayList<ProductsModel>) {
        this.productsList = productsList
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}