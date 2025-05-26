package woowacourse.shopping.view.main.adapter.recent

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.main.adapter.HistoryViewHolder
import woowacourse.shopping.view.main.state.HistoryState

class RecentProductAdapter(
    private val items: List<HistoryState>,
    private val handler: HistoryViewHolder.Handler,
) : RecyclerView.Adapter<HistoryViewHolder>() {
    init {
        Log.d("dsadasas", "init")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryViewHolder {
        return HistoryViewHolder.of(parent, handler)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
