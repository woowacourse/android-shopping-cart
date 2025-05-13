package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods

class GoodsAdapter(
    private val items: List<Goods>,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder {
        return GoodsViewHolder(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
