package woowacourse.shopping.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartItemRecyclerViewAdapter(
    private var products: List<Product> = emptyList(),
    private var cartItems: List<CartItem> = emptyList(),
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<CartItemRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            HolderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClick = { id -> onClick(id) },
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val product = products[position]
        val item = cartItems.find { it.productId == product.id } ?: CartItem(product.id, 0)
        holder.bind(product, cartItem = item)
        holder.binding.listener = onClickCartItemCounter
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(
        newProducts: List<Product>,
        newCartItems: List<CartItem>,
    ) {
        this.products = newProducts
        this.cartItems = newCartItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: HolderCartBinding,
        private val onClick: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: Product,
            cartItem: CartItem,
        ) {
            binding.product = product
            binding.cartItem = cartItem
            binding.cartProductDelete.setOnClickListener { onClick(product.id) }
        }
    }
}
