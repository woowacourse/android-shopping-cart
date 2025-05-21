package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    private val binding: ItemLoadMoreBinding,
    loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.loadMoreClickListener =
            loadMoreClickListener
    }

    companion object {
        fun create(
            parent: ViewGroup,
            loadMoreClickListener: LoadMoreClickListener,
        ): LoadMoreViewHolder {
            val binding =
                ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadMoreViewHolder(binding, loadMoreClickListener)
        }
    }
}
