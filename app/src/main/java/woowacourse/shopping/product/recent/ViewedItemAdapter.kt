package woowacourse.shopping.product.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.catalog.ProductUiModel

class ViewedItemAdapter(
    private val viewedProducts: List<ProductUiModel>,
) : RecyclerView.Adapter<ViewedItemHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewedItemHolder = ViewedItemHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewedItemHolder,
        position: Int,
    ) = holder.bind(viewedProducts[position])

    override fun getItemCount(): Int = viewedProducts.size
}
