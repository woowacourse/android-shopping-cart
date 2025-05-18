package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.Product

class CartAdapter(
    private val itemsInCart: MutableList<Product> = mutableListOf(),
    private val onProductRemoveClickListener: OnProductRemoveClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val holder =
            ItemProductInCartBinding.inflate(
                inflater,
                parent,
                false,
            )
        return CartViewHolder(holder, onProductRemoveClickListener)
    }

    override fun getItemCount(): Int = itemsInCart.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(itemsInCart[position])
    }

    fun updateProductsView(items: List<Product>) {
        val oldSize = itemsInCart.size
        itemsInCart.clear()
        notifyItemRangeRemoved(0, oldSize)

        itemsInCart.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }
}
