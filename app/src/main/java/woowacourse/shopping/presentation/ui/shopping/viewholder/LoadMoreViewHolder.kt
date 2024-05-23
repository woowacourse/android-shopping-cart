package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding,
    actionHandler: ShoppingActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.actionHandler = actionHandler
    }
}
