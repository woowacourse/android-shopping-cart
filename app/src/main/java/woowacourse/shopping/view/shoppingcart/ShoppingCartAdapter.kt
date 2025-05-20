package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.Product

class ShoppingCartAdapter(
    private val onProductRemove: (Product, Int) -> Unit,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var products: List<Product> = listOf()
    private var currentPage: Int = 0

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item, currentPage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemShoppingCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, onProductRemove)
    }

    fun updateProducts(page: Page<Product>) {
        val previousCount = itemCount
        products = page.items
        currentPage = page.currentPage
        notifyItemRangeChanged(0, previousCount)
        notifyItemRangeRemoved(previousCount - itemCount, previousCount - itemCount)
    }
}
