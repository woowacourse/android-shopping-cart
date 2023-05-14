package woowacourse.shopping.feature.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.feature.list.item.ProductView

abstract class ItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(productView: ProductView, onClick: (ProductView) -> Unit)
}
