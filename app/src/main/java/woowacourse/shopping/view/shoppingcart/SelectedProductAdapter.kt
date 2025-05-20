package woowacourse.shopping.view.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct

class SelectedProductAdapter(
    private val removeEventListener: OnRemoveProductListener,
) : RecyclerView.Adapter<SelectedProductViewHolder>() {
    private val shoppingProducts = mutableListOf<ShoppingProduct>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SelectedProductViewHolder = SelectedProductViewHolder.from(parent, removeEventListener)

    override fun getItemCount(): Int = shoppingProducts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<ShoppingProduct>) {
        shoppingProducts.clear()
        shoppingProducts.addAll(newItems)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: SelectedProductViewHolder,
        position: Int,
    ) {
        holder.bind(shoppingProducts[position])
    }
}
