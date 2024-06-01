package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderHistoryBinding
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class HistoryListAdapter(
    private val actionHandler: ProductListActionHandler,
) : ListAdapter<History, HistoryListAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
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
        fun bind(history: History) {
            binding.history = history
            binding.actionHandler = actionHandler
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<History>() {
                override fun areItemsTheSame(
                    old: History,
                    new: History,
                ): Boolean {
                    return old == new
                }

                override fun areContentsTheSame(
                    old: History,
                    new: History,
                ): Boolean {
                    return old == new
                }
            }
    }
}
