package woowacourse.shopping.feature.main.load

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding

class LoadViewHolder(
    private val binding: ItemLoadMoreProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(onClick: () -> Unit) {
        binding.loadMore.setOnClickListener { onClick.invoke() }
    }
}
