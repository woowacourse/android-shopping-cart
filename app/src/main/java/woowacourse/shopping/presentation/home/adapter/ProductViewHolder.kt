package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.home.HomeActionHandler
import woowacourse.shopping.presentation.uistate.Order

class ProductViewHolder(
    private val binding: ItemProductBinding,
    homeActionHandler: HomeActionHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.homeActionHandler = homeActionHandler
    }

    fun bind(order: Order) {
        binding.order = order
    }
}
