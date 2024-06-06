package woowacourse.shopping.ui.products.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductsAdapter(private val onClickRecentProductItem: OnClickRecentProductItem) :
    ListAdapter<RecentProductUiModel, RecentProductViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onClickRecentProductItem)
    }

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<RecentProductUiModel>() {
                override fun areItemsTheSame(
                    oldItem: RecentProductUiModel,
                    newItem: RecentProductUiModel,
                ): Boolean {
                    return oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: RecentProductUiModel,
                    newItem: RecentProductUiModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
