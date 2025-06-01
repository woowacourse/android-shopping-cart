package woowacourse.shopping.view.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.cart.CartItem

class RecentProductsAdapter(
    private val cartItems: MutableList<CartItem> = mutableListOf(),
    private val recentProductClickListener: (CartItem) -> Unit,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.from(parent, recentProductClickListener)

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(cartItems[position])
    }

    fun updateRecentProductsView(updatedProducts: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(updatedProducts)
//        notifyItemInserted(products.lastIndex)
        notifyDataSetChanged()
    }
}
