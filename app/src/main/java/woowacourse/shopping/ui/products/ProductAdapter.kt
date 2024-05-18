package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductAdapter(
    private val itemClickListener: (Long) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()
    private var currentOffset = DEFAULT_OFFSET

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun setData(newProducts: List<Product>) {
        products.addAll(newProducts)
        notifyItemRangeInserted(
            newProducts.size * currentOffset,
            newProducts.size,
        )
        currentOffset++
    }

    companion object {
        private const val DEFAULT_OFFSET = 1
    }
}
