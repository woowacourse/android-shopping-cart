package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel

class ShoppingCartRecyclerAdapter(
    products: List<ProductUiModel>,
    private val onRemoved: (id: Int) -> Unit,
    private val showingRule: ShowingRule,
    private val onPageChanged: (pageNumber: Int) -> Unit
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    private val shoppingCartProducts: MutableList<ProductUiModel> = products.toMutableList()
    private var currentPage = Page()
    private val showingProducts: List<ProductUiModel>
        get() = showingRule.of(
            products = shoppingCartProducts,
            page = currentPage
        )

    init {
        onPageChanged(currentPage.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {
        return ShoppingCartItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(
            productUiModel = showingProducts[position],
            onRemoveClicked = ::removeItem
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem(position: Int) {
        onRemoved(shoppingCartProducts[position].id)
        shoppingCartProducts.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun toNextPage(products: List<ProductUiModel>) {
        shoppingCartProducts.addAll(products)
        currentPage = currentPage.next()
        onPageChanged(currentPage.value)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun toPreviousPage() {
        currentPage = currentPage.prev()
        onPageChanged(currentPage.value)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = showingProducts.size
}
