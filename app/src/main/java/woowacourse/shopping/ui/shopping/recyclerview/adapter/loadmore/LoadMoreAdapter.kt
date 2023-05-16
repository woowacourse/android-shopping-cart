package woowacourse.shopping.ui.shopping.recyclerview.adapter.loadmore

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.ShoppingViewType

class LoadMoreAdapter(
    private val onItemClick: () -> Unit,
    private val button: MutableList<Any> = mutableListOf(),
) : RecyclerView.Adapter<LoadMoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadMoreViewHolder =
        LoadMoreViewHolder(parent) { onItemClick() }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, position: Int) {}

    override fun getItemCount(): Int = button.size

    override fun getItemViewType(position: Int): Int = ShoppingViewType.MORE_BUTTON.value

    fun showButton() {
        if (button.size == BUTTON_COUNT) return

        button.add(Any())
        notifyItemInserted(0)
    }

    fun hideButton() {
        if (button.size == 0) return

        button.clear()
        notifyItemRemoved(0)
    }

    companion object {
        private const val BUTTON_COUNT = 1
    }
}
