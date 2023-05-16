package woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingViewType

class RecentProductWrapperAdapter(
    private val recentProductAdapter: RecentProductAdapter,
    private val container: MutableList<Any> = mutableListOf(),
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductWrapperViewHolder = RecentProductWrapperViewHolder(parent, recentProductAdapter)

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {}

    override fun getItemCount(): Int = container.size

    override fun getItemViewType(position: Int): Int = ShoppingViewType.RECENT_PRODUCTS.value

    fun submitList(recentProductList: List<UiRecentProduct>) {
        recentProductAdapter.submitList(recentProductList)
        updateRecentProductWrapperVisible(recentProductList)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecentProductWrapperVisible(recentProductList: List<UiRecentProduct>) {
        container.clear()
        if (recentProductList.isNotEmpty()) container.add(Any())
        notifyDataSetChanged()
    }
}
