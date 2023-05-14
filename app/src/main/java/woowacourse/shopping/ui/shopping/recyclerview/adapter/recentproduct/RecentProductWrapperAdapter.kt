package woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingViewType

class RecentProductWrapperAdapter(
    private val recentProductAdapter: RecentProductAdapter,
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductWrapperViewHolder = RecentProductWrapperViewHolder(parent, recentProductAdapter)

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {}

    override fun getItemCount(): Int = if (recentProductAdapter.itemCount > 0) 1 else 0

    override fun getItemViewType(position: Int): Int = ShoppingViewType.RECENT_PRODUCTS.value

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(recentProductList: List<UiRecentProduct>) {
        recentProductAdapter.submitList(recentProductList)
        notifyDataSetChanged()
    }

    fun submit(recentProduct: UiRecentProduct) {
        val origin = recentProductAdapter.currentList
        submitList(listOf(recentProduct) + origin)
    }
}
