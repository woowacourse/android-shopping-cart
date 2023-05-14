package woowacourse.shopping.feature.list.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.ProductView

abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(productView: ProductView, onClick: (ProductView) -> Unit)
}
