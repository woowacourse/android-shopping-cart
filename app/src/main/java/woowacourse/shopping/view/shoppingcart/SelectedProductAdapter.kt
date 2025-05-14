package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct

class SelectedProductAdapter(
    private val products: List<ShoppingProduct>,
    private val eventListener: OnRemoveProductListener,
) : RecyclerView.Adapter<SelectedProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SelectedProductViewHolder = SelectedProductViewHolder.from(parent, eventListener)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: SelectedProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }
}
