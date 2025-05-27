package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.view.uimodel.QuantityInfo
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartRecyclerViewItems

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var products: List<ShoppingCartItemUiModel> = listOf()
    private var currentPage: Int = 0
    var quantityInfo = QuantityInfo<ShoppingCartItemUiModel>()
        private set

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = products[position]
        val quantity = quantityInfo[item]
        holder.bind(item, currentPage, quantity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemShoppingCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, handler)
    }

    fun updateProducts(items: ShoppingCartRecyclerViewItems) {
        val previousCount = itemCount
        products = items.page.items
        currentPage = items.page.currentPage
        quantityInfo = items.quantityInfo
        notifyItemRangeChanged(0, previousCount)
        notifyItemRangeRemoved(previousCount - itemCount, previousCount - itemCount)
    }
}
