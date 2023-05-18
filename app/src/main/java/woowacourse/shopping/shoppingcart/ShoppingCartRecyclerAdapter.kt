package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.Page
import woowacourse.shopping.util.CART_PRODUCT_TO_READ

class ShoppingCartRecyclerAdapter(
    products: List<CartProductUiModel>,
    private val onRemoved: (id: Int) -> Unit,
    private val showingRule: ShowingRule,
    private val updatePageState: (pageNumber: Int, totalSize: Int) -> Unit,
    private var totalSize: Int,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    private val shoppingCartProducts: MutableList<CartProductUiModel> = products.toMutableList()
    private var currentPage = Page()
    private val showingProducts: List<CartProductUiModel>
        get() = showingRule.of(
            products = shoppingCartProducts,
            page = currentPage,
        )

    init {
        updatePageState(currentPage.value, totalSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {
        return ShoppingCartItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(
            cartProductUiModel = showingProducts[position],
            onRemoveClicked = ::removeItem,
        )
    }

    private fun removeItem(position: Int) {
        onRemoved(shoppingCartProducts[position].product.id)
        shoppingCartProducts.removeAt(position)
        if (showingProducts.isEmpty()) {
            currentPage = currentPage.prev()
        }
        totalSize--
        updatePageState(currentPage.value, totalSize)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun toNextPage(products: List<CartProductUiModel>) {
        shoppingCartProducts.addAll(products)
        currentPage = currentPage.next()
        updatePageState(currentPage.value, totalSize)
        notifyDataSetChanged()
    }

    fun toPreviousPage() {
        currentPage = currentPage.prev()
        updatePageState(currentPage.value, totalSize)
        notifyItemRangeChanged(0, CART_PRODUCT_TO_READ)
    }

    override fun getItemCount(): Int = showingProducts.size
}
