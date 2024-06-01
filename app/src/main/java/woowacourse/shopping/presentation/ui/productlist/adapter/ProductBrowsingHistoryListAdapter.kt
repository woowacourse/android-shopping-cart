package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderHistoryBinding
import woowacourse.shopping.domain.model.ProductBrowsingHistory
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class ProductBrowsingHistoryListAdapter(
    private val actionHandler: ProductListActionHandler,
) : ListAdapter<ProductBrowsingHistory, ProductBrowsingHistoryListAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(
            HolderHistoryBinding.inflate(inflater, parent, false),
            actionHandler,
        )
    }

    override fun onBindViewHolder(
        viewHolder: HistoryViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    class HistoryViewHolder(
        val binding: HolderHistoryBinding,
        val actionHandler: ProductListActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productBrowsingHistory: ProductBrowsingHistory) {
            binding.history = productBrowsingHistory
            binding.actionHandler = actionHandler
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ProductBrowsingHistory>() {
                override fun areItemsTheSame(
                    old: ProductBrowsingHistory,
                    new: ProductBrowsingHistory,
                ): Boolean {
                    return old == new
                }

                override fun areContentsTheSame(
                    old: ProductBrowsingHistory,
                    new: ProductBrowsingHistory,
                ): Boolean {
                    return old == new
                }
            }
    }
}
