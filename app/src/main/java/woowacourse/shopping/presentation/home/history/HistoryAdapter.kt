package woowacourse.shopping.presentation.home.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.databinding.ItemProductHistoryBinding
import woowacourse.shopping.presentation.BindableAdapter
import woowacourse.shopping.presentation.home.products.HomeItemEventListener

class HistoryAdapter(
    private val homeItemClickListener: HomeItemEventListener,
) : ListAdapter<RecentProduct, HistoryAdapter.HistoryItemViewHolder>(HistoryDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int,
    ): HistoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductHistoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_product_history, parent, false)
        return HistoryItemViewHolder(binding, homeItemClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(
        holder: HistoryItemViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class HistoryItemViewHolder(
        private val binding: ItemProductHistoryBinding,
        homeItemClickListener: HomeItemEventListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.homeItemClickListener = homeItemClickListener
        }

        fun bind(historyItem: RecentProduct) {
            binding.recentProduct = historyItem
        }
    }
}
