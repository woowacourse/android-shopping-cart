package woowacourse.shopping.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.list.ViewType
import woowacourse.shopping.list.viewholder.LoadMoreViewHolder

class LoadMoreAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<LoadMoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadMoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoreBinding.inflate(inflater, parent, false)
        return LoadMoreViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadMoreViewHolder, position: Int) = holder.bind()

    override fun getItemViewType(position: Int): Int = ViewType.LOAD_MORE.ordinal
}
