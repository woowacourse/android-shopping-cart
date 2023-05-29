package woowacourse.shopping.ui.basket

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiBasketProduct
import woowacourse.shopping.ui.model.UiProduct

class BasketViewHolder(
    private val binding: ItemBasketBinding,
    onItemClick: (UiBasketProduct) -> Unit,
    minusClickListener: (UiProduct) -> Unit,
    plusClickListener: (UiProduct) -> Unit,
    onCheckedChangeListener: (UiBasketProduct, Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCloseClickListener = onItemClick
        binding.counterBasket.plusClickListener = plusClickListener
        binding.counterBasket.minusClickListener = minusClickListener
        binding.onCheckedChangeListener = onCheckedChangeListener
    }

    fun bind(item: UiBasketProduct) {
        binding.basketProduct = item
        binding.counterBasket.product = item.product
        binding.counterBasket.count = item.count.value
        binding.basketProductTotalPrice = item.toDomain().getTotalPrice().value
    }
}
