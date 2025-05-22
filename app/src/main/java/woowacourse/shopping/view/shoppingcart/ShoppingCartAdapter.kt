package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.view.uimodel.QuantityInfo
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

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

    fun updateProducts(page: Page<ShoppingCartItemUiModel>) {
        val previousCount = itemCount
        products = page.items
        currentPage = page.currentPage
        quantityInfo =
            QuantityInfo(
                page.items.associateWith { MutableLiveData(it.quantity) },
            )
        notifyItemRangeChanged(0, previousCount)
        notifyItemRangeRemoved(previousCount - itemCount, previousCount - itemCount)
    }
}
