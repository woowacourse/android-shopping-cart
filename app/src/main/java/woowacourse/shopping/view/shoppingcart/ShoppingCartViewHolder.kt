package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shopping.domain.Count
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.view.customview.CounterView
import woowacourse.shopping.view.customview.CounterViewEventListener

class ShoppingCartViewHolder(
    parent: ViewGroup,
    private val onClickRemove: (CartProductUIModel) -> Unit,
    private val onClickCountButton: (CartProductUIModel, TextView) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
) {
    private val binding = ItemCartProductBinding.bind(itemView)
    private lateinit var cartProduct: CartProductUIModel

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProduct)
        }

        binding.counterView.listener = object : CounterViewEventListener {
            override fun updateCount(counterView: CounterView, count: Int) {
                binding.counterView.updateCountView()
                onClickCountButton(
                    CartProductUIModel(cartProduct.productUIModel, Count(count)),
                    binding.tvPrice
                )
            }
        }
    }

    fun bind(item: CartProductUIModel) {
        cartProduct = item
        binding.cartProduct = cartProduct
        binding.counterView.initCount(cartProduct.count.value)
        binding.counterView.updateCountView()
    }
}
