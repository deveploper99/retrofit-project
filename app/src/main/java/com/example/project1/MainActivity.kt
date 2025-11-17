package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project1.adapter.ProductAdapter
import com.example.project1.databinding.ActivityMainBinding
import com.example.project1.model.ProductModel
import com.example.project1.profile.ProfileActivity
import com.example.project1.retrofit.ProductViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var pVModel: ProductViewModel

    private val productList = mutableListOf<ProductModel>()
    private val filteredList = mutableListOf<ProductModel>()  // For search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileImageProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.cartIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // ViewModel
        pVModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // Adapter setup
        adapter = ProductAdapter(
            filteredList,
            onFavoriteClick = { position ->
                val product = filteredList[position]
                product.isFavorite = !product.isFavorite
                adapter.notifyItemChanged(position)
            },
            onItemClick = { product ->
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("product", product)
                startActivity(intent)
            }
        )

        binding.productGrid.layoutManager = GridLayoutManager(this, 2)
        binding.productGrid.adapter = adapter

        // LiveData observe
        pVModel.producs.observe(this) { producs ->
            producs?.let {
                productList.clear()
                productList.addAll(it)

                filteredList.clear()
                filteredList.addAll(it)  // Initially show all
                adapter.notifyDataSetChanged()
            }
        }

        pVModel.fetchProducts()

        // Search functionality
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterProducts(query)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterProducts(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(productList)
        } else {
            filteredList.addAll(productList.filter { it.title.contains(query, ignoreCase = true) })
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
        val cartCount = prefs.getInt("cartCount", 0)
        binding.cartBadge.text = cartCount.toString()
        binding.cartBadge.visibility = if (cartCount > 0) View.VISIBLE else View.GONE
    }
}
