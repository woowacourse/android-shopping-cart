package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderHistoryBinding
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class HistoryListAdapter(
    private var histories: List<History>? = null,
    private val actionHandler: ProductListActionHandler,
) : RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>() {
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

    override fun getItemCount(): Int = histories?.size ?: 0

    override fun onBindViewHolder(
        viewHolder: HistoryViewHolder,
        position: Int,
    ) {
        histories?.get(position)?.let {
            viewHolder.bind(it)
        }
    }

    fun updateHistoryList(newHistories: List<History>) {
        histories = newHistories
        notifyDataSetChanged()
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
}
