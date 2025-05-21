package woowacourse.shopping.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = listOf()
    private var quantity: Map<Product, MutableLiveData<Int>> = mapOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = products[position]
        val quantityLiveData = quantity[item] ?: return
        holder.bind(item, quantityLiveData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ),
                parent,
                false,
            )
        val handler = ProductEventHandlerImpl(parent.context, binding)
        return ProductsViewHolder(binding, handler)
    }

    fun updateProducts(mainRecyclerViewProduct: MainRecyclerViewProduct) {
        val shoppingCartItems = mainRecyclerViewProduct.shoppingCartItems
        val page = mainRecyclerViewProduct.page

        val quantityMap =
            page.items.associateWith { product ->
                MutableLiveData(
                    shoppingCartItems.items.find { it.product == product }?.quantity ?: DEFAULT_QUANTITY,
                )
            }

        products += mainRecyclerViewProduct.page.items
        quantity += quantityMap
        notifyItemInserted(itemCount)
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}
