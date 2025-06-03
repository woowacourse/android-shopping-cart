package woowacourse.shopping.presentation.products.ui.viewholder

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.products.ui.ProductsItem

abstract class ProductsItemViewHolder<ITEM : ProductsItem, BINDING : ViewDataBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root) {
    protected lateinit var item: ProductsItem

    @CallSuper
    open fun bind(item: ITEM) {
        this.item = item
    }
}
