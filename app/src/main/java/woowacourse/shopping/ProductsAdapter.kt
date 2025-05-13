package woowacourse.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductsAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            tvProductName.text = products[position].name
            tvProductPrice.text = products[position].price.toString()
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(
        val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root){
        init {

        }
    }
}


