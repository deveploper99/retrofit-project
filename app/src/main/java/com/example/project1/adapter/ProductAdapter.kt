package com.example.project1.adapter

import com.example.project1.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.KeyPosition
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.databinding.ProductItemBinding
import com.example.project1.model.ProductModel

class ProductAdapter(
    private val productList: List<ProductModel>,
    private val onFavoriteClick:(position: Int) -> Unit

): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


     inner class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {

        val product = productList[position]
        val binding = holder.binding

        Glide.with(binding.productImage.context)
            .load(product.thumbnail)
            .into(binding.productImage)

        binding.productTitle.text = product.title
        binding.productPrice.text = "$${product.price}"
        binding.productCategory.text = product.category
        binding.productRating.text = product.rating

        if (product.isFavorite){
            binding.favIcon.setImageResource(R.drawable.ic_heart)
        }else{
            binding.favIcon.setImageResource(R.drawable.ic_heart_filled)
        }

        binding.favIcon.setOnClickListener {
            onFavoriteClick(position)
        }



    }

    override fun getItemCount(): Int = productList.size
}