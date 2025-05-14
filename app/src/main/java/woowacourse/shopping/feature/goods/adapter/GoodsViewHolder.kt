package woowacourse.shopping.feature.goods.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Goods

class GoodsViewHolder(
    private val binding: ItemGoodsBinding,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(goods: Goods) {
        binding.goods = goods
        itemView.setOnClickListener { goodsClickListener.onClickGoods(goods) }
    }

    interface GoodsClickListener {
        fun onClickGoods(goods: Goods)
    }
}
