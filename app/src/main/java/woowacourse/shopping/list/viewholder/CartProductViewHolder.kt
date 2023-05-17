package woowacourse.shopping.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductState

class CartProductViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding = binding as ItemCartProductBinding

    fun bind(cartProductState: CartProductState, onClick: (CartProductState) -> Unit) {
        binding.cartProduct = cartProductState
        binding.cartClearImageView.setOnClickListener { onClick(cartProductState) }
    }
}
