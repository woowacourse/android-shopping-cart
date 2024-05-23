package woowacourse.shopping.presentation.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.ProductListItem

class CartAdapter(
    private val cartHandler: CartHandler,
    private var carts: List<ProductListItem.ShoppingProductItem> = emptyList(),
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding, cartHandler)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(carts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<ProductListItem.ShoppingProductItem>) {
        carts = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carts.size
    }
}

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartHandler: CartHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductListItem.ShoppingProductItem) {
        binding.cartHandler = cartHandler
        binding.product = item
    }
}
