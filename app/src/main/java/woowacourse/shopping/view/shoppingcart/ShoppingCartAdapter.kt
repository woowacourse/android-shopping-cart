package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.ShoppingCartItem

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var products: List<ShoppingCartItem> = listOf()
    private var currentPage: Int = 0
    var quantityMap: Map<ShoppingCartItem, MutableLiveData<Int>> = mapOf()
        private set

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = products[position]
        handler.item = item
        handler.page = currentPage
        holder.bind(item, currentPage, quantityMap[item]!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemShoppingCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, handler)
    }

    fun updateProducts(page: Page<ShoppingCartItem>) {
        val previousCount = itemCount
        products = page.items
        currentPage = page.currentPage
        quantityMap = page.items.associateWith { MutableLiveData(it.quantity) }
        notifyItemRangeChanged(0, previousCount)
        notifyItemRangeRemoved(previousCount - itemCount, previousCount - itemCount)
    }
}
