package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class ShoppingCartRecyclerAdapter(
    products: List<ProductUiModel>,
    private val onRemoved: (id: Int) -> Unit,
    private val showingRule: ShowingRule
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    private val shoppingCartProducts: MutableList<ProductUiModel> = products.toMutableList()
    private var currentPage = 0
    private val showingProducts: List<ProductUiModel>
        get() = showingRule.of(
            products = shoppingCartProducts,
            page = currentPage
        )

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
        currentPage++
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun toPreviousPage() {
        currentPage--
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = showingProducts.size
}
