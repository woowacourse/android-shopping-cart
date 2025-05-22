package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.GoodsUiModel

class LatestGoodsAdapter(
    private val items: List<GoodsUiModel>,
) : RecyclerView.Adapter<LatestGoodsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LatestGoodsViewHolder {
        return LatestGoodsViewHolder(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: LatestGoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
