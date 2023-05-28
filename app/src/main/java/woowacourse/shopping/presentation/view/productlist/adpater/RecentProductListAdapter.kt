package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.productlist.viewholder.RecentProductListViewHolder

class RecentProductListAdapter(
    private val onProductClick: (Long) -> Unit
) : ListAdapter<RecentProductModel, RecentProductListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductListViewHolder {
        return RecentProductListViewHolder(parent) {
            onProductClick(getItem(it).product.id)
        }
    }

    override fun onBindViewHolder(holder: RecentProductListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    fun setItems(newItems: List<RecentProductModel>) {
        submitList(newItems)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecentProductModel>() {
            override fun areItemsTheSame(
                oldItem: RecentProductModel,
                newItem: RecentProductModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecentProductModel,
                newItem: RecentProductModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
