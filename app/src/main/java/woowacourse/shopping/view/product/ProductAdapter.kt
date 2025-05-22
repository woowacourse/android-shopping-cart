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
    private var products: List<Product> = emptyList()

    override fun getItemViewType(position: Int): Int =
        if (position < products.size) {
            ViewType.PRODUCTS.ordinal
        } else {
            ViewType.SHOW_MORE.ordinal
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
            is ProductViewHolder -> {
                if (position < products.size) {
                    holder.bind(products[position])
                }
            }

            is ShowMoreViewHolder -> holder
        }
    }

    override fun getItemCount(): Int {
        val hasShowMore = products.size % LIMIT_COUNT == 0 && products.isNotEmpty()
        return products.size + if (hasShowMore) 1 else 0
    }

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    companion object {
        private const val LIMIT_COUNT = 20
    }
}
