package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Goods

class GoodsViewHolder(
    private val binding: ItemGoodsBinding,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            binding.goods?.let { goodsClickListener.onClickGoods(it) }
        }
    }

    fun bind(goods: Goods) {
        binding.goods = goods
    }

    companion object {
        fun from(
            parent: ViewGroup,
            goodsClickListener: GoodsClickListener,
        ): GoodsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGoodsBinding.inflate(inflater, parent, false)
            return GoodsViewHolder(binding, goodsClickListener)
        }
    }
}
