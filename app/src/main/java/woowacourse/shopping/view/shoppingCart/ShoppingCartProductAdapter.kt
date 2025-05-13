package woowacourse.shopping.view.shoppingCart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.product.Product

class ShoppingCartProductAdapter(
    private val items: List<Product>,
) : RecyclerView.Adapter<ShoppingCartProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartProductViewHolder = ShoppingCartProductViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ShoppingCartProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
