package com.example.project1



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
import com.example.project1.profile.ProfileActivity
import com.example.project1.retrofit.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var pVModel: ProductViewModel

    private var cartCount = 0

    private val productList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cartIcon.setOnClickListener {
            val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
            val cartItems = prefs.getStringSet("cartItems", setOf()) ?: setOf()

            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                val items = cartItems.joinToString("\n")
                AlertDialog.Builder(this)
                    .setTitle("Cart Items")
                    .setMessage(items)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }






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

       // adapter = ProductAdapter(mutableListOf())

        // Adapter setup with mutable list
//        adapter = ProductAdapter(productList) { position ->
//            val product = productList[position]
//            product.isFavorite = !product.isFavorite
//            adapter.notifyItemChanged(position)
//
//        }

        adapter = ProductAdapter(
            productList,

            onFavoriteClick = { position ->
                val product = productList[position]
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
                productList.addAll(it)  // Update existing list
                adapter.notifyDataSetChanged()
            }
        }

        pVModel.fetchProducts()

    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("cartPrefs", MODE_PRIVATE)
        val cartCount = prefs.getInt("cartCount", 0)
        binding.cartBadge.text = cartCount.toString()
        binding.cartBadge.visibility = if (cartCount > 0) View.VISIBLE else View.GONE

    }



}
