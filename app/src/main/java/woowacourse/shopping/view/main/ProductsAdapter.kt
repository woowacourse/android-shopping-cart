package woowacourse.shopping.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsAdapter(
    private val handler: (Product) -> Unit,
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = listOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context))
        return ProductsViewHolder(binding, handler)
    }

    fun updateProducts(newProducts: List<Product>) {
        products += newProducts
        notifyItemInserted(itemCount)
    }
}
