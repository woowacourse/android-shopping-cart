package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct

class SelectedProductAdapter(
    products: List<ShoppingProduct>,
    private val eventListener: OnRemoveProductListener,
) : RecyclerView.Adapter<SelectedProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SelectedProductViewHolder = SelectedProductViewHolder.from(parent, eventListener)

    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    fun removeItem(shoppingProduct: ShoppingProduct) {
        notifyItemRemoved(products.indexOf(shoppingProduct))
        products.remove(shoppingProduct)
    }

    override fun onBindViewHolder(
        holder: SelectedProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }
}
