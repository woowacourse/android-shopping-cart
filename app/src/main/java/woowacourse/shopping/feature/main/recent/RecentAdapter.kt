package woowacourse.shopping.feature.main.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.RecentProductUiModel

class RecentAdapter(
    private val onClick: (recentProduct: RecentProductUiModel) -> Unit
) : ListAdapter<RecentProductUiModel, RecentViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecentProductBinding>(
            layoutInflater,
            R.layout.item_recent_product,
            parent,
            false
        )
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecentProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: RecentProductUiModel,
                newItem: RecentProductUiModel
            ): Boolean {
                return oldItem.productUiModel.id == newItem.productUiModel.id
            }

            override fun areContentsTheSame(
                oldItem: RecentProductUiModel,
                newItem: RecentProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
