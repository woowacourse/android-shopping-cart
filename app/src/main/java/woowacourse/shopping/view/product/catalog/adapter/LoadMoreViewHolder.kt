package woowacourse.shopping.view.product.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding,
    eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = eventHandler
    }

    interface EventHandler {
        fun onMoreClick()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventHandler: EventHandler,
        ): LoadMoreViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
            return LoadMoreViewHolder(binding, eventHandler)
        }
    }
}
