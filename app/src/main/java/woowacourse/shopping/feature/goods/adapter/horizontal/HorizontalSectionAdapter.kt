package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.goods.GoodsViewModel

class HorizontalSectionAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: GoodsViewModel,
    private val recentlyViewedGoodsAdapter: RecentlyViewedGoodsAdapter,
) : RecyclerView.Adapter<HorizontalSelectionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HorizontalSelectionViewHolder = HorizontalSelectionViewHolder.from(parent, lifecycleOwner, viewModel)

    override fun onBindViewHolder(
        holder: HorizontalSelectionViewHolder,
        position: Int,
    ) {
        holder.binding.viewModel = viewModel
        with(holder.binding.horizontalRecycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentlyViewedGoodsAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount(): Int = 1
}
