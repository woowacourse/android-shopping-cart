package woowacourse.shopping.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductState
import woowacourse.shopping.model.CartProductState.Companion.MAX_COUNT_VALUE
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE

class CartProductViewHolder(
    binding: ViewBinding,
    private val onCartProductDeleteClick: (CartProductState) -> Unit,
    private val onCountMinusClick: (CartProductState) -> Unit,
    private val onCountPlusClick: (CartProductState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val binding = binding as ItemCartProductBinding

    fun bind(cartProductState: CartProductState) {
        binding.cartProduct = cartProductState
        binding.counterView.count = cartProductState.count

        binding.cartClearImageView.setOnClickListener { onCartProductDeleteClick(cartProductState) }
        binding.counterView.plusClickListener = {
            binding.counterView.count++
            if (MAX_COUNT_VALUE < binding.counterView.count) binding.counterView.count--
            cartProductState.count = binding.counterView.count
            onCountPlusClick(cartProductState)
        }
        binding.counterView.minusClickListener = {
            binding.counterView.count--
            if (binding.counterView.count < MIN_COUNT_VALUE) binding.counterView.count++
            cartProductState.count = binding.counterView.count
            onCountMinusClick(cartProductState)
        }
    }
}
