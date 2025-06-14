package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.ShoppingCartItem

class CartAdapter(
    private val onProductRemove: (Int) -> Unit,
    private val onQuantityChange: (Int, Int) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var itemsInCart: List<ShoppingCartItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductInCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding, onProductRemove, onQuantityChange)
    }

    override fun getItemCount(): Int = itemsInCart.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(itemsInCart[position])
    }

    fun updateCartItemsView(items: List<ShoppingCartItem>) {
        itemsInCart = items
        notifyDataSetChanged()
    }
}
