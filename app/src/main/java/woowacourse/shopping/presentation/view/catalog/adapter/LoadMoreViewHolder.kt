package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding,
    eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = eventListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CatalogAdapter.CatalogEventListener,
        ): LoadMoreViewHolder {
            val binding =
                ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadMoreViewHolder(binding, eventListener)
        }
    }
}
