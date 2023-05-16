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
    private val onProductCountPlus: (product: ShoppingCartProductUiModel) -> (Unit),
    private val onProductCountMinus: (product: ShoppingCartProductUiModel) -> (Unit),
    private val onPageChanged: (pageNumber: Int) -> Unit,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    private val shoppingCartProducts: MutableList<ShoppingCartProductUiModel> =
        products.toMutableList()
    private var currentPage = Page()
    private val endPage: Page
        get() = ShowingCartProductsPageRule.getPageOfEnd(
            totalProductsSize = shoppingCartProducts.size
        )
    private val showingProducts: MutableList<ShoppingCartProductUiModel>
        get() = ShowingCartProductsPageRule.getProductsOfPage(
            products = shoppingCartProducts,
            page = currentPage
        ).toMutableList()

    init {
        onPageChanged(currentPage.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {

        return ShoppingCartItemViewHolder.from(parent).apply {
            setOnClicked(
                onRemoveClicked = ::removeItem,
                onPlusImageClicked = onProductCountPlus,
                onMinusImageClicked = onProductCountMinus
            )
        }
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(product = showingProducts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem(product: ShoppingCartProductUiModel) {
        onRemoved(product.id)
        shoppingCartProducts.remove(product)
        notifyDataSetChanged()
    }

    fun updateItem(product: ShoppingCartProductUiModel) {
        val itemIndex = shoppingCartProducts.indexOfFirst { it.id == product.id }

        shoppingCartProducts.removeAt(itemIndex)
        shoppingCartProducts.add(itemIndex, product)
        notifyItemChanged(showingProducts.indexOfFirst { it.id == product.id })
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
