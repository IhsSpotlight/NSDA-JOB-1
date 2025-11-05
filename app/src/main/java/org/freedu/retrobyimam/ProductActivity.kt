package org.freedu.retrobyimam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel

    // --- FIX: Create separate variables for each RecyclerView and its adapter ---
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var trendingRecyclerView: RecyclerView // For productRV1

    private lateinit var productsAdapter: ProductAdapter
    private lateinit var trendingAdapter: ProductAdapter // A separate adapter instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // 1. Initialize ViewModel
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        // 2. Initialize Views
        productsRecyclerView = findViewById(R.id.productRV)
        trendingRecyclerView = findViewById(R.id.productRV1) // Find the second RecyclerView
        val refreshBtn: FloatingActionButton = findViewById(R.id.refreshBtn)

        // 3. Setup both RecyclerViews
        setupRecyclerViews()

        // 4. Observe LiveData for updates
        observeViewModel()

        // 5. Set click listener for refresh
        refreshBtn.setOnClickListener {
            lifecycleScope.launch {
                productViewModel.fetchProducts()
            }
        }
    }

    private fun setupRecyclerViews() {
        // --- Setup for the FIRST RecyclerView (productRV) ---
        productsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productsAdapter = ProductAdapter(emptyList())
        productsRecyclerView.adapter = productsAdapter

        // --- Setup for the SECOND RecyclerView (productRV1) ---
        trendingRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trendingAdapter = ProductAdapter(emptyList()) // Create a new adapter instance
        trendingRecyclerView.adapter = trendingAdapter
    }

    private fun observeViewModel() {
        productViewModel.products.observe(this) { products ->
            products?.let {
                // --- FIX: Update BOTH adapters with the data ---
                productsAdapter.updateProducts(it)

                // For now, we'll show the same products. You could shuffle or reverse the list for variety.
                // For example: trendingAdapter.updateProducts(it.shuffled())
                trendingAdapter.updateProducts(it.shuffled())
            }
        }
    }
}
