package com.example.project1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project1.databinding.ActivityDetailsBinding
import com.example.project1.model.ProductModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("product") as? ProductModel
        product?.let { showProductDetails(it) }
    }

    private fun showProductDetails(product: ProductModel) {
        binding.title.text = product.title
        binding.price.text = "$${product.price}"
        binding.description.text = product.description
        binding.ratingText.text = product.rating
        binding.badge.text = if (product.stock > 50) "Best Seller" else "Limited Stock"

        Glide.with(this)
            .load(product.images.firstOrNull() ?: product.thumbnail)
            .into(binding.productImage)

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.btnAddToCart.setOnClickListener {
            val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
            val cartJson = prefs.getString("cartJson", null)

            val cartList: MutableList<ProductModel> = try {
                if (cartJson != null)
                    gson.fromJson(cartJson, object : TypeToken<MutableList<ProductModel>>() {}.type)
                else mutableListOf()
            } catch (e: Exception) {
                mutableListOf()
            }

            // যদি product আগে থেকে থাকে তাহলে quantity++
            val existing = cartList.find { it.title == product.title }
            if (existing != null) existing.quantity++ else cartList.add(product)

            prefs.edit().putString("cartJson", gson.toJson(cartList)).apply()
            prefs.edit().putInt("cartCount", cartList.size).apply()

            Toast.makeText(this, "${product.title} added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
