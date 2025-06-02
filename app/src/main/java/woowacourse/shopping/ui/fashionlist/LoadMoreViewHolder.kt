package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.LoadMoreItemBinding

class LoadMoreViewHolder(
    binding: LoadMoreItemBinding,
    viewModel: ProductListViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
    }
}
