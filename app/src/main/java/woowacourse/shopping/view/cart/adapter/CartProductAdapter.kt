package woowacourse.shopping.view.cart.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct

class CartProductAdapter(
    private val eventHandler: CartProductEventHandler,
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private val shoppingProducts = mutableListOf<ShoppingProduct>()
    private var hasNext: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder = CartProductViewHolder.from(parent, eventHandler)

    override fun getItemCount(): Int = shoppingProducts.size

    fun removeItem(product: ShoppingProduct) {
        notifyItemRemoved(shoppingProducts.indexOf(product))
        shoppingProducts.remove(product)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(
        newItems: List<ShoppingProduct>,
        hasNext: Boolean,
    ) {
        shoppingProducts.clear()
        shoppingProducts.addAll(newItems)
        this.hasNext = hasNext
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(shoppingProducts[position])
    }
}
