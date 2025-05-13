package woowacourse.shopping.presentation.goods.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Goods

class GoodsViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)) {
    private val binding = ItemGoodsBinding.bind(itemView)

    fun bind(goods: Goods) {
        binding.goods = goods
    }
}
