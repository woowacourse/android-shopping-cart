package woowacourse.shopping.view.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.OnClickShoppingCart
import woowacourse.shopping.view.cart.adapter.viewholder.ShoppingCartViewHolder
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter

class ShoppingCartAdapter(
    private val onClickShoppingCart: OnClickShoppingCart,
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val loadLastItem: () -> Unit,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()
    private var productPosition: HashMap<Long, Int> = hashMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val view =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(view, onClickCartItemCounter, onClickShoppingCart)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = cartItems[position]
        productPosition[item.product.id] = position
        item.product.updateItemSelector(true)
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCartItems(
        hasLastItem: Boolean,
        cartItems: List<CartItem>,
    ) {
        if (hasLastItem) {
            loadLastItem()
        }
        this.cartItems = cartItems
        notifyDataSetChanged()
    }

    fun updateCartItem(productId: Long) {
        val position = productPosition[productId]
        if (position != null) {
            notifyItemChanged(position)
        }
    }

    fun deleteCartItem(productId: Long) {
        val position = productPosition[productId]
        if (position != null) {
            notifyItemChanged(position)
            productPosition.remove(productId)
        }
    }
}
