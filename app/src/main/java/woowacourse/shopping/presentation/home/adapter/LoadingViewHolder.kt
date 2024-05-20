package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.presentation.home.LoadStatus

class LoadingViewHolder(
    private val binding: ItemLoadMoreBinding,
    loadClickListener: LoadClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.loadClickListener = loadClickListener
    }

    fun bind(loadStatus: LoadStatus) {
        binding.loadStatus = loadStatus
    }
}
