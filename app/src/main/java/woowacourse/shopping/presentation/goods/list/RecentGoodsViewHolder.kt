package woowacourse.shopping.presentation.goods.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentGoodsBinding
import woowacourse.shopping.domain.model.Goods

class RecentGoodsViewHolder(
    parent: ViewGroup,
    clickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recent_goods, parent, false),
    ) {
    private val binding = ItemRecentGoodsBinding.bind(itemView)

    init {
        binding.clickListener = clickListener
    }

    fun bind(goods: Goods) {
        binding.goods = goods
    }
}
