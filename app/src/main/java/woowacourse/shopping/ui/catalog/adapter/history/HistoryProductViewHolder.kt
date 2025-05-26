package woowacourse.shopping.ui.catalog.adapter.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryProductBinding
import woowacourse.shopping.domain.model.HistoryProduct

class HistoryProductViewHolder(
    private val binding: ItemHistoryProductBinding,
    onClickHandler: OnClickHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun bind(historyProduct: HistoryProduct) {
        binding.historyProduct = historyProduct
    }

    fun interface OnClickHandler {
        fun onHistoryProductClick(id: Int)
    }
}
