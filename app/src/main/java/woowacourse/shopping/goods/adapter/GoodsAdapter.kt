package woowacourse.shopping.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Goods

class GoodsAdapter(private val items: List<Goods>) : RecyclerView.Adapter<GoodsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGoodsBinding.inflate(inflater, parent, false)
        return GoodsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int
    ) {
        val item: Goods = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}

