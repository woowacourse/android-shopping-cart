package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shopping.domain.Count
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.view.customview.CounterView
import woowacourse.shopping.view.customview.CounterViewEventListener

class ShoppingCartViewHolder(
    parent: ViewGroup,
    private val clickListener: ShoppingCartClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
) {
    private val binding = ItemCartProductBinding.bind(itemView)
    private lateinit var cartProduct: CartProductUIModel

    init {
        binding.ivCancel.setOnClickListener {
            clickListener.onClickRemove(cartProduct)
        }

        binding.cbProduct.setOnClickListener {
            val changedState = binding.cbProduct.isChecked
            cartProduct = CartProductUIModel(cartProduct.productUIModel, cartProduct.count, changedState)
            clickListener.onClickCheckBox(cartProduct)
        }

        binding.counterView.listener = object : CounterViewEventListener {
            override fun updateCount(counterView: CounterView, count: Int): Int {
                if (count == IMPOSSIBLE_COUNT_VALUE) {
                    return MINIMUM_COUNT_VALUE
                }
                cartProduct = CartProductUIModel(cartProduct.productUIModel, Count(count), cartProduct.isSelected)
                clickListener.onClickCountButton(cartProduct, binding.tvPrice)
                return count
            }
        }
    }

    fun bind(item: CartProductUIModel) {
        cartProduct = item
        binding.cartProduct = cartProduct
        binding.counterView.initCount(cartProduct.count.value)
        binding.counterView.updateCountView()
        binding.cbProduct.isChecked = cartProduct.isSelected
    }

    companion object {
        private const val IMPOSSIBLE_COUNT_VALUE = 0
        private const val MINIMUM_COUNT_VALUE = 1
    }
}
