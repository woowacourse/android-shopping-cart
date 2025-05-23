package woowacourse.shopping.feature.goods.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    private val binding: ItemLoadMoreBinding,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.goodsClickListener = goodsClickListener
    }
}
