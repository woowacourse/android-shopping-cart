package woowacourse.shopping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductBinding

class ProductsAdapter(
    private val products: List<Product>,
    private val context: Context,
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.binding.apply {
            tvProductName.text = products[position].name
            tvProductPrice.text = products[position].price.toString()
            Glide
                .with(context)
                .load(products[position].imageUrl)
                .placeholder(R.drawable.maxim_arabica)
                .fallback(R.drawable.maxim_arabica)
                .error(R.drawable.maxim_arabica)
                .into(ivProduct)
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(
        val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
        }
    }
}
