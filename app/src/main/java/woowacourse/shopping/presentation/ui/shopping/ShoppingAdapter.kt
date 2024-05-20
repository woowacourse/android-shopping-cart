package woowacourse.shopping.presentation.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ShoppingAdapter(
    private val clickListener: ShoppingItemClickListener,
) : RecyclerView.Adapter<ShoppingViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        val product = products[position]
        return holder.bind(product, clickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun loadData(newProducts: List<Product>) {
        val oldSize = products.size
        val newSize = newProducts.size
        val addSize = newSize - oldSize
        products += newProducts.subList(oldSize, newSize)
        notifyItemRangeInserted(oldSize, addSize)
    }
}
