package woowacourse.shopping.view.shoppingCart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.product.Product

class ShoppingCartProductAdapter(
    private val onRemoveProduct: (product: Product) -> Unit,
) : RecyclerView.Adapter<ShoppingCartProductViewHolder>() {
    private var items: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartProductViewHolder = ShoppingCartProductViewHolder.from(parent, onRemoveProduct)

    override fun onBindViewHolder(
        holder: ShoppingCartProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(items: List<Product>) {
        this.items = items
        notifyDataSetChanged()
    }
}
