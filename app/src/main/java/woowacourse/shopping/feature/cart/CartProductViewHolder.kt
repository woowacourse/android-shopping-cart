package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common_ui.CounterView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductUiModel

class CartProductViewHolder private constructor(
    private val binding: ItemCartProductBinding,
    listener: CartProductClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener

        binding.counterView.countStateChangeListener =
            object : CounterView.OnCountStateChangeListener {
                override fun onCountChanged(counterNavigationView: CounterView?, count: Int) {
                    binding.cartProduct?.let { listener.onCartCountChanged(it.cartId, count) }
                }
            }
    }

    fun bind(cartProduct: CartProductUiModel) {
        binding.cartProduct = cartProduct
        binding.counterView.setCountState(cartProduct.productUiModel.count, false)
        binding.purchaseCheckBox.isChecked = cartProduct.checked
    }

    companion object {
        fun create(
            parent: ViewGroup,
            cartProductClickListener: CartProductClickListener
        ): CartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(layoutInflater, parent, false)
            return CartProductViewHolder(binding, cartProductClickListener)
        }
    }
}
