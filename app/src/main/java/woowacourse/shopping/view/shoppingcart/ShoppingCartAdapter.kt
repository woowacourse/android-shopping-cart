package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.Product

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var products: List<Product> = listOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemShoppingCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, handler)
    }

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
    }
}

interface ShoppingCartEventHandler {
    fun onProductRemove(product: Product)

    fun onPaginationPrevious()

    fun onPaginationNext()
}
