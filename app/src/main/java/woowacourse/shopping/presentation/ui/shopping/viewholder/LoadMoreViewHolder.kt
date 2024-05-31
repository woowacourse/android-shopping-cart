package woowacourse.shopping.presentation.ui.shopping.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding,
    actionHandler: ShoppingActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.actionHandler = actionHandler
    }
}
