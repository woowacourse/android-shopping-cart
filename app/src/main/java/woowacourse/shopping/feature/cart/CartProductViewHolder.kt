package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductState
import woowacourse.shopping.model.CartProductState.Companion.MAX_COUNT_VALUE
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE

class CartProductViewHolder(
    binding: ViewDataBinding,
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

    companion object {
        fun createInstance(
            parent: ViewGroup,
            onCartProductDeleteClick: (CartProductState) -> Unit,
            onCountMinusClick: (CartProductState) -> Unit,
            onCountPlusClick: (CartProductState) -> Unit
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(
                binding, onCartProductDeleteClick, onCountMinusClick, onCountPlusClick
            )
        }
    }
}
