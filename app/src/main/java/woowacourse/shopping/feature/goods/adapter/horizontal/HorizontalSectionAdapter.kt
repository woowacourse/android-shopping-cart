package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HorizontalSectionAdapter(
    private val recentlyViewedGoodsAdapter: RecentlyViewedGoodsAdapter,
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
            adapter = recentlyViewedGoodsAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount(): Int = 1
}
