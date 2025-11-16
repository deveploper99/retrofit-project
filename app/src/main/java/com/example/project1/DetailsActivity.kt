package com.example.project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
//import com.example.project1.adapter.SimilarProductAdapter
import com.example.project1.databinding.ActivityDetailsBinding
import com.example.project1.model.ProductModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    //private lateinit var similarAdapter: SimilarProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Serializable থেকে Product object নাও
        val product = intent.getSerializableExtra("product") as? ProductModel
        product?.let { showProductDetails(it) }
    }

    private fun showProductDetails(product: ProductModel) {
        // Title, Price, Description
        binding.title.text = product.title
        binding.price.text = "$${product.price}"
        binding.description.text = product.description
        binding.ratingText.text = product.rating

        // Badge
        binding.badge.text = if (product.stock > 50) "Best Seller" else "Limited Stock"

        // Product Image
        Glide.with(this)
            .load(product.images.firstOrNull() ?: product.thumbnail)
            .into(binding.productImage)

        // Similar Products (dummy list or same category)
//        similarAdapter = SimilarProductAdapter(productList = listOf(product)) {
//            // Click listener for similar items (optional)
//        }
//        binding.rvSimilar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvSimilar.adapter = similarAdapter

        // Back button
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // Bottom buttons (optional click listeners)
        binding.btnAddToCart.setOnClickListener {
            // Add to cart logic
        }
        binding.btnBuyNow.setOnClickListener {
            // Buy now logic
        }
    }
}
