package com.example.project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project1.adapter.ProductAdapter
import com.example.project1.databinding.ActivityMainBinding
import com.example.project1.model.ProductModel
import com.example.project1.retrofit.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var pVModel: ProductViewModel

    private val productList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel
        pVModel = ViewModelProvider(this).get(ProductViewModel::class.java)

       // adapter = ProductAdapter(mutableListOf())

        // Adapter setup with mutable list
        adapter = ProductAdapter(productList) { position ->
            val product = productList[position]
            product.isFavorite = !product.isFavorite
            adapter.notifyItemChanged(position)
        }

        binding.productGrid.layoutManager = GridLayoutManager(this, 2)
        binding.productGrid.adapter = adapter

        // LiveData observe
        pVModel.producs.observe(this) { producs ->
            producs?.let {
                productList.clear()
                productList.addAll(it)  // Update existing list
                adapter.notifyDataSetChanged()
            }
        }

        pVModel.fetchProducts()

    }
}
