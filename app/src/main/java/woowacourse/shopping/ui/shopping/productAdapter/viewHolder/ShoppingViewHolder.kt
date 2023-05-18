package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(productItemType: ProductsItemType)
}
