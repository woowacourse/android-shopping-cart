package woowacourse.shopping.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.databinding.ItemProductHistoryBinding
import woowacourse.shopping.presentation.BindableAdapter

class HistoryAdapter(
    private val homeItemClickListener: HomeItemEventListener,
) : RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>(), BindableAdapter<RecentProduct> {
    private var historyItems: List<RecentProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int,
    ): HistoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductHistoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_product_history, parent, false)
        return HistoryItemViewHolder(binding, homeItemClickListener)
    }

    override fun getItemCount(): Int = historyItems.size

    override fun onBindViewHolder(
        holder: HistoryItemViewHolder,
        position: Int,
    ) {
        holder.bind(historyItems[position])
    }

    override fun setData(data: List<RecentProduct>) {
        val previousSize = historyItems.size
        historyItems = data
        notifyDataSetChanged()
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
