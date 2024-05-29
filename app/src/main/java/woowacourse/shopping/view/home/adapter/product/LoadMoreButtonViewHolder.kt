package woowacourse.shopping.view.home.adapter.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreButtonBinding
import woowacourse.shopping.view.home.HomeClickListener

class LoadMoreButtonViewHolder(private val binding: ItemLoadMoreButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonClickListener: HomeClickListener) {
        binding.clickListener = buttonClickListener
    }
}
