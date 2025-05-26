package woowacourse.shopping.feature.goods.adapter.history

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryContainerBinding
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.feature.goods.adapter.GoodsClickListener

class HistoryContainerViewHolder(
    private val binding: ItemHistoryContainerBinding,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(histories: List<Cart>) {
        val adapter = HistoryAdapter(goodsClickListener)
        adapter.setItems(histories)
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
    }
}
