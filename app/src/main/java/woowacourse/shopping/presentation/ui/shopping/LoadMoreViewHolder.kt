package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding,
    viewModel: ShoppingViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
    }
}
