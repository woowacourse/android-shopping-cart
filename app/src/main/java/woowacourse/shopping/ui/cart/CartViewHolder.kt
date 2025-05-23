package woowacourse.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.cart.CartProduct

class CartViewHolder private constructor(
    private val binding: CartItemBinding,
    cartClickListener: CartClickListener,
    private val viewModel: CartViewModel
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartClickListener = cartClickListener
        binding.viewModel = viewModel
    }

    fun bind(item: CartProduct) {
        binding.cartProduct = item
    }

    companion object {
        fun create(
            parent: ViewGroup,
            cartClickListener: CartClickListener,
            viewModel: CartViewModel
        ): CartViewHolder {
            return CartViewHolder(
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.cart_item, parent, false
                ),
                cartClickListener = cartClickListener,
                viewModel = viewModel
            )
        }
    }
}
