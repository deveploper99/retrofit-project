package com.example.project1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.databinding.ItemCartBinding
import com.example.project1.model.ProductModel

class CartAdapter(
    private var cartList: MutableList<ProductModel>,
    private val onRemoveClick: (position: Int) -> Unit,
    private val priceUpdateCallback: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartList[position]
        val binding = holder.binding

        binding.productName.text = product.title
        binding.productPrice.text = "$${product.price}"
        binding.txtQty.text = product.quantity.toString()

        Glide.with(binding.itemImage1.context)
            .load(product.thumbnail)
            .into(binding.itemImage1)

        // Quantity buttons
        binding.btnMinus.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                notifyItemChanged(position)
                priceUpdateCallback()
            } else {
                cartList.removeAt(position)
                notifyItemRemoved(position)
                onRemoveClick(position)
                priceUpdateCallback()
            }
        }

        binding.btnPlus.setOnClickListener {
            product.quantity++
            notifyItemChanged(position)
            priceUpdateCallback()
        }

        // Long press to remove
        binding.root.setOnLongClickListener {
            cartList.removeAt(position)
            notifyItemRemoved(position)
            onRemoveClick(position)
            priceUpdateCallback()
            true
        }
    }

    override fun getItemCount(): Int = cartList.size

    fun updateList(newList: MutableList<ProductModel>) {
        cartList = newList
        notifyDataSetChanged()
    }
}
