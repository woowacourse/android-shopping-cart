package woowacourse.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsAdapter(
    private val products: List<Product>,
    private val handler: ProductsEventHandler,
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, handler)
    }
}

class ViewHolder(
    private val binding: ItemProductBinding,
    handler: ProductsEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(item: Product) {
        binding.product = item
    }
}

interface ProductsEventHandler {
    fun onProductSelected(product: Product)
}
