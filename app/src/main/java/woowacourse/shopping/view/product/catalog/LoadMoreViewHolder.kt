package woowacourse.shopping.view.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    private val binding: ItemLoadMoreBinding,
    eventListener: OnLoadEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onLoadClick = eventListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnLoadEventListener,
        ): LoadMoreViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
            return LoadMoreViewHolder(binding, eventListener)
        }
    }
}
