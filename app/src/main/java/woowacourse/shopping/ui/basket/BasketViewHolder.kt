package woowacourse.shopping.ui.basket

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.model.UiProduct

class BasketViewHolder(private val binding: ItemBasketBinding, onItemClick: (UiProduct) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var product: UiProduct

    init {
        binding.ivClose.setOnClickListener { onItemClick(product) }
    }

    fun bind(item: UiProduct) {
        product = item
        binding.product = item
    }
}
