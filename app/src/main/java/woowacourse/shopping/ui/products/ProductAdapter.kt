package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductAdapter(
    private val viewModel: ProductContentsViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: (Long) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, lifecycleOwner, itemClickListener)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position], viewModel)
    }

    fun setData(newProducts: List<Product>) {
        val currentProductsSize = products.size
        products.clear()
        products.addAll(newProducts)
        notifyItemRangeInserted(currentProductsSize, newProducts.size)
    }
}
