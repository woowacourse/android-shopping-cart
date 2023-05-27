package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemMoreBinding

class MoreItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    onClickItem: () -> Unit,
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_more, parent, false),
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, showMoreProductItem: () -> Unit) :
        this(parent, LayoutInflater.from(parent.context), showMoreProductItem)

    val binding = ItemMoreBinding.bind(itemView)

    init {
        itemView.setOnClickListener { onClickItem() }
    }
}
