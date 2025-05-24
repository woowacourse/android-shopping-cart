package woowacourse.shopping.feature.goods.adapter.history

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryContainerBinding
import woowacourse.shopping.domain.model.History

class HistoryContainerViewHolder(
    private val binding: ItemHistoryContainerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(histories: List<History>) {
        val adapter = HistoryAdapter()
        adapter.setItems(histories)
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
    }
}
