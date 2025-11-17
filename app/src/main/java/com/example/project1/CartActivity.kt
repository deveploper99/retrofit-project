package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.adapter.CartAdapter
import com.example.project1.databinding.ActivityCartBinding
import com.example.project1.model.ProductModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val gson = Gson()
    private var cartList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
        loadCartItems()


    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartList,
            onRemoveClick = { position -> removeItemFromCart(position) },
            priceUpdateCallback = { updatePriceDetails() }
        )
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCart.adapter = cartAdapter
    }

    private fun loadCartItems() {
        val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
        val cartJson = prefs.getString("cartJson", null)
        cartList = if (cartJson != null)
            gson.fromJson(cartJson, object : TypeToken<MutableList<ProductModel>>() {}.type)
        else
            mutableListOf()

        if (cartList.isEmpty()) Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()

        cartAdapter.updateList(cartList)
        updatePriceDetails()
    }

    private fun removeItemFromCart(position: Int) {
        val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
        if (position < cartList.size) {
            cartList.removeAt(position)
            prefs.edit().putString("cartJson", gson.toJson(cartList)).apply()
            prefs.edit().putInt("cartCount", cartList.size).apply()
            cartAdapter.updateList(cartList)
            updatePriceDetails()
            Toast.makeText(this, "Item removed from cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePriceDetails() {
        var subtotal = 0.0
        for (item in cartList) {
            subtotal += item.price * item.quantity
        }
        val discount = 0.0 // Optional: set your discount logic
        val total = subtotal - discount

        binding.apply {
            subTotalText.text = "$${"%.2f".format(subtotal)}"
            discountText.text = "$${"%.2f".format(discount)}"
            totalText.text = "$${"%.2f".format(total)}"
        }

    }
    }
