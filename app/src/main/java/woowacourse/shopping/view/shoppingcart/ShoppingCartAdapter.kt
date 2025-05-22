package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, handler)
    }

    fun updateProducts(newProducts: List<Product>) {
        val oldCount = itemCount
        products.clear()
        products.addAll(newProducts)
        notifyItemRangeChanged(0, oldCount)
    }
}
