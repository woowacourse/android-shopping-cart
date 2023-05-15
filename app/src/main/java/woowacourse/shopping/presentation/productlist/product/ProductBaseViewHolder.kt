package woowacourse.shopping.presentation.productlist.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel

abstract class ProductBaseViewHolder(
    val view: View,
    showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(view) {

    protected var _productModel: ProductModel? = null
    protected val productModel get() = _productModel!!

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    abstract fun bind(product: ProductModel)
}
