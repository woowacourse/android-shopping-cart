package woowacourse.shopping.presentation.goods.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemLatestGoodsBinding
import woowacourse.shopping.presentation.model.GoodsUiModel

class LatestGoodsViewHolder(
    parent: ViewGroup,
    latestGoodsClickListener: LatestGoodsClickListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_latest_goods, parent, false)) {
    private val binding = ItemLatestGoodsBinding.bind(itemView)

    init {
        binding.latestGoodsClickListener = latestGoodsClickListener
    }

    fun bind(goods: GoodsUiModel) {
        binding.goods = goods
    }
}
