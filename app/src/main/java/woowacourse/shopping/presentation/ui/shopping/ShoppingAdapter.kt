package woowacourse.shopping.presentation.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingProduct

class ShoppingAdapter(
    private val shoppingEventHandler: ShoppingEventHandler,
    private val shoppingItemCountHandler: ShoppingItemCountHandler,
) : RecyclerView.Adapter<ShoppingViewHolder>() {
    private var products: List<Product> = emptyList()
    private var shoppingProducts: List<ShoppingProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        val product = products[position]
        val shoppingProduct = shoppingProducts[position]
        return holder.bind(product, shoppingProduct, shoppingEventHandler, shoppingItemCountHandler)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun loadData(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun loadShoppingProductData(shoppingProducts: List<ShoppingProduct>) {
        this.shoppingProducts = shoppingProducts
        notifyDataSetChanged()
    }
}
