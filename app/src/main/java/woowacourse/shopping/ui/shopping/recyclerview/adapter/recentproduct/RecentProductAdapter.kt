package woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.util.diffutil.RecentProductDiffUtil

class RecentProductAdapter(private val onItemClick: (UiRecentProduct) -> Unit) :
    ListAdapter<UiRecentProduct, RecentProductViewHolder>(RecentProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder =
        RecentProductViewHolder(parent) { pos -> onItemClick(currentList[pos]) }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
