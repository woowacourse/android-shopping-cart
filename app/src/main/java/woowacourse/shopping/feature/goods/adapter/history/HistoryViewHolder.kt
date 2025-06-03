package woowacourse.shopping.feature.goods.adapter.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryBinding
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.feature.goods.adapter.GoodsClickListener

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(history: History) {
        binding.history = history
        binding.goodsClickListener = goodsClickListener
    }
}
