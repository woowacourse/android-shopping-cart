package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(binding: ItemLoadMoreBinding, onClick: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.loadMoreButton.setOnClickListener {
            onClick()
        }
    }
}
