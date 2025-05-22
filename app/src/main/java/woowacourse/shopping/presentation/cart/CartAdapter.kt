package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.model.CartItem

class CartAdapter(
    private val cartCounterClickListener: CartCounterClickListener,
    private val cartPageClickListener: CartPageClickListener,
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
        return CartViewHolder(binding, cartCounterClickListener, cartPageClickListener)
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

    class CartViewHolder(
        val binding: ItemCartProductBinding,
        private val cartCounterClickListener: CartCounterClickListener,
        private val cartPageClickListener: CartPageClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.cartItem = cartItem
            binding.cartPageClickListener = cartPageClickListener
            binding.counterClickListener = cartCounterClickListener
            binding.executePendingBindings()
        }
    }
}
