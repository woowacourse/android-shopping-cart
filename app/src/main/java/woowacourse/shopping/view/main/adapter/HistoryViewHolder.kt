package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryBinding
import woowacourse.shopping.view.main.state.HistoryState

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistoryState) {
        with(binding) {
            model = item
            eventHandler = handler
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            handler: Handler,
        ): HistoryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHistoryBinding.inflate(inflater, parent, false)
            return HistoryViewHolder(binding, handler)
        }
    }

    interface Handler {
        fun onClickHistory(productId: Long)
    }
}
