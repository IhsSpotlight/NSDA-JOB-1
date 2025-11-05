package org.freedu.retrobyimam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// 1. CRITICAL FIX: Change 'val' to 'var' to allow the list to be updated
class ProductAdapter(private var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    // 2. YOUR ADDED METHOD: This is now correctly implemented
    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage:ImageView = itemView.findViewById(R.id.productImage)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            // 3. ADDED SAFETY CHECK: Prevents crashes if a product has no images
            if (product.images.isNotEmpty()) {
                Glide.with(itemView)
                    .load(product.images[0])
                    .into(productImage)
            } else {
                // Optional: set a default image if none are provided
                // productImage.setImageResource(R.drawable.placeholder)
            }
            productName.text = product.title
            productPrice.text = "$${product.price}"
        }
    }


}
