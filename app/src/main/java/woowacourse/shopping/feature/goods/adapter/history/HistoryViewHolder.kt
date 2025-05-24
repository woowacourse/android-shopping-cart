package woowacourse.shopping.feature.goods.adapter.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryBinding
import woowacourse.shopping.domain.model.History

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(history: History) {
        binding.history = history
    }
}
