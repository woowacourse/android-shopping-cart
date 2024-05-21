package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreButtonBinding

class LoadMoreButtonViewHolder(private val binding: ItemLoadMoreButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonClickListener: ShoppingClickListener) {
        binding.clickListener = buttonClickListener
    }
}
