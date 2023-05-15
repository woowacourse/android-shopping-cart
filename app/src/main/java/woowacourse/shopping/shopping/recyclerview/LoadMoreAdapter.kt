package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<LoadMoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadMoreViewHolder {
        return LoadMoreViewHolder(
            ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context)), onClick
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadMoreViewHolder, position: Int) = Unit

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 3
    }
}
