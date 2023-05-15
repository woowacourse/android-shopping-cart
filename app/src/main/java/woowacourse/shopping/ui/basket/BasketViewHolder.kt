package woowacourse.shopping.ui.basket

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketViewHolder(
    private val binding: ItemBasketBinding,
    onItemClick: (UiBasketProduct) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var basketProduct: UiBasketProduct

    init {
        binding.ivClose.setOnClickListener { onItemClick(basketProduct) }
    }

    fun bind(item: UiBasketProduct) {
        basketProduct = item
        binding.basketProduct = item
    }
}
