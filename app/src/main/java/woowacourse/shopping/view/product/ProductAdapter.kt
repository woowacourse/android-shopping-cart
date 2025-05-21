package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.ViewItems.ViewType

class ProductAdapter(
    private val onShowMore: () -> Boolean,
    private val onSelectedProduct: (Product) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()

    override fun getItemViewType(position: Int): Int =
        when (position != 0 && position % LIMIT_COUNT == 0) {
            true -> 1
            false -> 0
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        when (ViewType.from(viewType)) {
            ViewType.PRODUCTS ->
                ProductViewHolder.from(parent, onSelectedProduct)

            ViewType.SHOW_MORE -> ShowMoreViewHolder.from(parent, onShowMore)
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(products[position])
            is ShowMoreViewHolder -> holder
        }
    }

    override fun getItemCount(): Int = products.size + (products.size / LIMIT_COUNT)

    fun setData(newProducts: List<Product>) {
        val previous = products.size
        products += newProducts
        notifyItemRangeInserted(previous, newProducts.size)
    }

    companion object {
        private const val LIMIT_COUNT = 20
    }
}
