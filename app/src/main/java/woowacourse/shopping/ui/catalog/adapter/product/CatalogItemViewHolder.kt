package woowacourse.shopping.ui.catalog.adapter.product

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class CatalogItemViewHolder<ITEM : CatalogItem, BINDING : ViewDataBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root) {
    protected lateinit var item: CatalogItem

    @CallSuper
    open fun bind(item: ITEM) {
        this.item = item
    }
}
