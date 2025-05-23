package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsClickListener

class HorizontalSectionAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<HorizontalSelectionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HorizontalSelectionViewHolder = HorizontalSelectionViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: HorizontalSelectionViewHolder,
        position: Int,
    ) {
        with(holder.binding.horizontalRecycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RecentlyViewedGoodsAdapter(goodsClickListener)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount(): Int = 1
}
