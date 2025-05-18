package woowacourse.shopping.view.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsAdapter(
    private val handler: InventoryEventHandler,
) : RecyclerView.Adapter<InventoryViewHolder>() {
    private var products: List<Product> = listOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: InventoryViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InventoryViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context))
        return InventoryViewHolder(binding, handler)
    }

    fun updateProducts(newProducts: List<Product>) {
        products += newProducts
    }
}

interface InventoryEventHandler {
    fun onProductSelected(product: Product)

    fun onLoadMoreProducts(page: Int)
}
