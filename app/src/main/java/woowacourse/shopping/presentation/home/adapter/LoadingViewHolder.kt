package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.presentation.home.HomeActionHandler

class LoadingViewHolder(
    private val binding: ItemLoadMoreBinding,
    homeActionHandler: HomeActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.homeActionHandler = homeActionHandler
    }

    fun bind(isLoadingAvailable: Boolean) {
        binding.isLoadingAvailable = isLoadingAvailable
    }
}
