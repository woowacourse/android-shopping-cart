package woowacourse.shopping.ui.shopping.recyclerview.adapter.product

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.ui.shopping.ShoppingViewType
import woowacourse.shopping.util.diffutil.ProductDiffUtil
import woowacourse.shopping.util.listener.ProductClickListener
import woowacourse.shopping.widget.ProductCounterView.OnClickListener

class ProductAdapter(
    private val productClickListener: ProductClickListener,
    private val counterClickListener: OnClickListener,
) : ListAdapter<UiCartProduct, ProductViewHolder>(ProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            parent = parent,
            productClickListener = productClickListener,
            counterClickListener = counterClickListener,
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = ShoppingViewType.PRODUCT.value
}
