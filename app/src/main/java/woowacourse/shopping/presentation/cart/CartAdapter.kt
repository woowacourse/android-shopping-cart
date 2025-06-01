package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.model.CartItem

class CartAdapter(
    private val cartCounterClickListener: CartCounterClickListener,
    private val cartPageClickListener: CartPageClickListener,
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartItemDiffCallback()) {
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
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        val binding: ItemCartProductBinding,
        cartCounterClickListener: CartCounterClickListener,
        cartPageClickListener: CartPageClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cartPageClickListener = cartPageClickListener
            binding.counterClickListener = cartCounterClickListener
        }

        fun bind(cartItem: CartItem) {
            binding.cartItem = cartItem
            binding.executePendingBindings()
        }
    }
}
