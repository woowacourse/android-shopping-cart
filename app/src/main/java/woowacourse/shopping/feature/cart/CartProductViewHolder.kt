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
    private val updateCount: (productId: Int, count: Int) -> Unit,
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
            binding.counterView.count = (++binding.counterView.count).coerceAtMost(MAX_COUNT_VALUE)
            cartProductState.count = binding.counterView.count
            updateCount(cartProductState.productId, binding.counterView.count)
        }
        binding.counterView.minusClickListener = {
            binding.counterView.count = (--binding.counterView.count).coerceAtLeast(MIN_COUNT_VALUE)
            cartProductState.count = binding.counterView.count
            updateCount(cartProductState.productId, binding.counterView.count)
        }
        binding.cartProductCheckBox.setOnClickListener {
            updateChecked(cartProductState.productId, binding.cartProductCheckBox.isChecked)
        }

        /*
            홀더 또한 뷰이기 때문에 값을 더하거나 빼는 등의 로직은 presenter와 같은 위치에 있는 것이 좋을 것 같으나
            어떻게 하면 좋을지 잘 모르겠습니다...
            이런 로직을 holder 밖으로 뺄 수 있는 방법이 있을까요?
         */
    }

    companion object {
        fun createInstance(
            parent: ViewGroup,
            onCartProductDeleteClick: (CartProductState) -> Unit,
            updateCount: (productId: Int, count: Int) -> Unit,
            updateChecked: (productId: Int, checked: Boolean) -> Unit
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(
                binding, onCartProductDeleteClick, updateCount, updateChecked
            )
        }
    }
}
