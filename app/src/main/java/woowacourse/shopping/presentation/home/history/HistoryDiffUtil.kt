package woowacourse.shopping.presentation.home.history

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.data.model.history.RecentProduct

object HistoryDiffUtil : DiffUtil.ItemCallback<RecentProduct>() {
    override fun areItemsTheSame(oldItem: RecentProduct, newItem: RecentProduct): Boolean {
        return oldItem.productHistory.id == newItem.productHistory.id
    }

    override fun areContentsTheSame(oldItem: RecentProduct, newItem: RecentProduct): Boolean {
        return oldItem == newItem
    }
}
