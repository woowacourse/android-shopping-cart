package woowacourse.shopping.ui.shopping.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.ui.model.UiProduct

class RecentProductAdapter(private val onItemClick: (UiProduct) -> Unit) :
    ListAdapter<UiProduct, RecentProductViewHolder>(productDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        val binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val productDiffUtil = object : DiffUtil.ItemCallback<UiProduct>() {
            override fun areItemsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem == newItem
        }
    }
}
