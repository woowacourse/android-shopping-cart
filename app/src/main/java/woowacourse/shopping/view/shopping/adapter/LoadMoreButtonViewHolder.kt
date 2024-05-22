package woowacourse.shopping.view.shopping.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreButtonBinding
import woowacourse.shopping.view.shopping.ShoppingClickListener

class LoadMoreButtonViewHolder(private val binding: ItemLoadMoreButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonClickListener: ShoppingClickListener) {
        binding.clickListener = buttonClickListener
    }
}
