package woowacourse.shopping.presentation.productlist.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel

abstract class ProductBaseViewHolder(
    val view: View,
    onItemClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }

    abstract fun bind(product: ProductModel)
}
