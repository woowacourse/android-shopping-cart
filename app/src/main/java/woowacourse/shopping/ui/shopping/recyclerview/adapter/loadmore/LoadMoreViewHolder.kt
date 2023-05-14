package woowacourse.shopping.ui.shopping.recyclerview.adapter.loadmore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.util.extension.setOnSingleClickListener

class LoadMoreViewHolder(
    parent: ViewGroup,
    onItemClick: () -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
) {

    init {
        itemView.setOnSingleClickListener { onItemClick() }
    }

}
