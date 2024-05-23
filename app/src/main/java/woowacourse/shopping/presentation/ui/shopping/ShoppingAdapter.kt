package woowacourse.shopping.presentation.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ShoppingAdapter(
    private val shoppingEventHandler: ShoppingEventHandler,
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
        return holder.bind(product, shoppingEventHandler)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun loadData(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}
