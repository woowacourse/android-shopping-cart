package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartRecyclerAdapter(
    products: List<ShoppingCartProductUiModel>,
    private val onRemoved: (id: Int) -> Unit,
    private val onAdded: () -> (Unit),
    private val onPageChanged: (pageNumber: Int) -> Unit
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    private val shoppingCartProducts: MutableList<ShoppingCartProductUiModel> =
        products.toMutableList()
    private var currentPage = Page()
    private val endPage: Page
        get() = ShowingCartProductsPageRule.getPageOfEnd(
            totalProductsSize = shoppingCartProducts.size
        )
    private val showingProducts: List<ShoppingCartProductUiModel>
        get() = ShowingCartProductsPageRule.getProductsOfPage(
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
            product = showingProducts[position],
            onRemoveClicked = ::removeItem
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem(position: Int) {
        onRemoved(shoppingCartProducts[position].id)
        shoppingCartProducts.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItems(products: List<ShoppingCartProductUiModel>) {
        shoppingCartProducts.addAll(products)
        currentPage = currentPage.next()
        changePage()
    }

    fun moveToNextPage() {
        if (currentPage == endPage) {
            return onAdded()
        }
        currentPage = currentPage.next()
        changePage()
    }

    fun moveToPreviousPage() {
        currentPage = currentPage.prev()
        changePage()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changePage() {
        onPageChanged(currentPage.value)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = showingProducts.size
}
