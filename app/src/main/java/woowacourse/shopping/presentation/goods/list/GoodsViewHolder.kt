package woowacourse.shopping.presentation.goods.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.presentation.model.GoodsUiModel

class GoodsViewHolder(
    parent: ViewGroup,
    goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)) {
    private val binding = ItemGoodsBinding.bind(itemView)

    init {
        binding.goodsClickListener = goodsClickListener
        binding.clGoodsCount.clickListener = goodsClickListener
    }

    fun bind(goods: GoodsUiModel) {
        binding.goods = goods
        binding.position = adapterPosition
        binding.clGoodsCount.count = goods.quantity
        binding.clGoodsCount.position = adapterPosition
    }
}
