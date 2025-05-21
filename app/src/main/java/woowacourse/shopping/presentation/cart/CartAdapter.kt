package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartItem

class CartAdapter(
    private val onDeleteClick: (CartItem) -> Unit,
) : RecyclerView.Adapter<CartAdapter.CartProductViewHolder>() {
    private var products: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartProductViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
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

    class CartProductViewHolder(
        val binding: ItemCartProductBinding,
        private val onDeleteClick: (CartItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var cartItem: CartItem

        init {
            binding.ibCartProductDelete.setOnClickListener {
                onDeleteClick(cartItem)
            }
        }

        fun bind(cartItem: CartItem) {
            binding.cartItem = cartItem
            binding.layoutCartQuantityBox.tvCartQuantity.text = cartItem.quantity.toString()
            this.cartItem = cartItem
        }
    }
}
