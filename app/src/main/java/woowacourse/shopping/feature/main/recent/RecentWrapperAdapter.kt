package woowacourse.shopping.feature.main.recent

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentWrapperAdapter(
    private val adapter: RecentAdapter
) : RecyclerView.Adapter<RecentWrapperViewHolder>() {
    private var lastScrollX = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentWrapperViewHolder {
        return RecentWrapperViewHolder.create(parent)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecentWrapperViewHolder, position: Int) {
        holder.bind(adapter, lastScrollX) { x ->
            lastScrollX = x
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun onSaveState(outState: Bundle) {
        outState.putInt(KEY_SCROLL_X, lastScrollX)
    }

    fun onRestoreState(savedState: Bundle) {
        lastScrollX = savedState.getInt(KEY_SCROLL_X)
    }

    companion object {
        private const val KEY_SCROLL_X = "KEY_SCROLL_X"
        const val VIEW_TYPE = 111
    }
}
