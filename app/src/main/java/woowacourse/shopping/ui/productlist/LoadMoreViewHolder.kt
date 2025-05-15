package woowacourse.shopping.ui.productlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.LoadMoreItemBinding

class LoadMoreViewHolder(
    binding: LoadMoreItemBinding,
    loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.loadMoreClickListener = loadMoreClickListener
    }
}
