package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductState

class CartProductViewHolder(
    binding: ViewDataBinding,
    private val onCartProductDeleteClick: (CartProductState) -> Unit,
    private val plusCount: (cartProductState: CartProductState) -> Unit,
    private val minusCount: (cartProductState: CartProductState) -> Unit,
    private val updateChecked: (productId: Int, checked: Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val binding = binding as ItemCartProductBinding

    fun bind(cartProductState: CartProductState) {
        binding.cartProduct = cartProductState
        binding.counterView.count = cartProductState.count

        binding.cartProductCheckBox.isChecked = cartProductState.checked
        binding.cartClearImageView.setOnClickListener {
            updateChecked(cartProductState.productId, false)
            onCartProductDeleteClick(cartProductState)
        }
        binding.counterView.plusClickListener = {
            plusCount(cartProductState)
            binding.counterView.count = cartProductState.count
        }
        binding.counterView.minusClickListener = {
            minusCount(cartProductState)
            binding.counterView.count = cartProductState.count
        }
        binding.cartProductCheckBox.setOnClickListener {
            updateChecked(cartProductState.productId, binding.cartProductCheckBox.isChecked)
        }
    }

    companion object {
        fun createInstance(
            parent: ViewGroup,
            onCartProductDeleteClick: (CartProductState) -> Unit,
            plusCount: (cartProductState: CartProductState) -> Unit,
            minusCount: (cartProductState: CartProductState) -> Unit,
            updateChecked: (productId: Int, checked: Boolean) -> Unit
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(
                binding, onCartProductDeleteClick, plusCount, minusCount, updateChecked
            )
        }
    }
}
