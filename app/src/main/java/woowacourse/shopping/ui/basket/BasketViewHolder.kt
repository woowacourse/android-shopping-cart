package woowacourse.shopping.ui.basket

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketViewHolder(
    private val binding: ItemBasketBinding,
    onItemClick: (UiBasketProduct) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCloseClickListener = onItemClick
    }

    fun bind(item: UiBasketProduct) {
        binding.basketProduct = item
    }
}
