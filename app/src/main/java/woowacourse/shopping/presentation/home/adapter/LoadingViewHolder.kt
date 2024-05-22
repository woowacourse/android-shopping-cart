package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.presentation.home.HomeActionHandler
import woowacourse.shopping.presentation.uistate.LoadStatus

class LoadingViewHolder(
    private val binding: ItemLoadMoreBinding,
    homeActionHandler: HomeActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.homeActionHandler = homeActionHandler
    }

    fun bind(loadStatus: LoadStatus) {
        binding.loadStatus = loadStatus
    }
}
