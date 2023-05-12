package woowacourse.shopping.ui.shopping.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.ProductsItemType

sealed class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(productItemType: ProductsItemType)
}
