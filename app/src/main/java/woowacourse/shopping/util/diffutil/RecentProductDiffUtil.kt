package woowacourse.shopping.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.UiRecentProduct

object RecentProductDiffUtil : DiffUtil.ItemCallback<UiRecentProduct>() {
    override fun areItemsTheSame(
        oldItem: UiRecentProduct,
        newItem: UiRecentProduct,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UiRecentProduct,
        newItem: UiRecentProduct,
    ): Boolean = oldItem == newItem
}
