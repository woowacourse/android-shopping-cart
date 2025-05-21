package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartItem

class CartAdapter(
    private val onDeleteClick: (CartItem) -> Unit,
    private val cartCounterClickListener: CartCounterClickListener,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var products: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartViewHolder(binding, onDeleteClick, cartCounterClickListener)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun submitList(newProducts: List<CartItem>) {
        val oldSize = products.size
        val newSize = newProducts.size

        if (oldSize > 0) {
            products.clear()
            notifyItemRangeRemoved(0, oldSize)
        }

        products.addAll(newProducts)
        notifyItemRangeInserted(0, newSize)
    }

    fun removeItem(id: Long) {
//        val index = products.indexOfFirst { it.productId == id }
//        if (index != -1) {
//            products.removeAt(index)
//            notifyItemRemoved(index)
//        }
    }

    class CartViewHolder(
        val binding: ItemCartProductBinding,
        private val onDeleteClick: (CartItem) -> Unit,
        private val cartCounterClickListener: CartCounterClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var cartItem: CartItem

        init {
            binding.ibCartProductDelete.setOnClickListener {
                onDeleteClick(cartItem)
            }
        }

        fun bind(cartItem: CartItem) {
            binding.cartItem = cartItem
//            binding.includedLayoutCart.tvCartQuantity.text = cartItem.quantity.toString()
//            binding.includedLayoutCart.clickListener =
//                cartQuantityClickListener
            binding.clickListener = cartCounterClickListener
            this.cartItem = cartItem
        }
    }
}
