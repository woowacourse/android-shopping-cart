package woowacourse.shopping.ui.productList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

class ProductRecyclerViewAdapter(
    private val onProductItemClickListener: OnProductItemClickListener,
    private val onItemQuantityChangeListener: OnItemQuantityChangeListener,
) : RecyclerView.Adapter<ProductsItemViewHolder>() {
    private var products: List<Product> = emptyList()
    private var productsIdCounts: List<ProductIdsCount> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder =
        ProductsItemViewHolder(
            HolderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductItemClickListener,
            onItemQuantityChangeListener,
        )

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateAllLoadedProducts(newData: List<Product>) {
        this.products = newData
        notifyItemRangeInserted(products.size, newData.size)
    }

    // TODO: 최적화
    @SuppressLint("NotifyDataSetChanged")
    fun updateProductInCart(productCountEvent: ProductCountEvent) {
        when (productCountEvent) {
            is ProductCountEvent.ProductCountCountChanged ->
                updateQuantity(productCountEvent.productId, productCountEvent.count)

            is ProductCountEvent.ProductCountCleared -> updateQuantity(productCountEvent.productId, 0)

            is ProductCountEvent.ProductCountAllCleared ->
                productsIdCounts = productsIdCounts.map { it.copy(quantity = 0) }
        }
        notifyDataSetChanged()
    }

    // TODO: 최적화
    @SuppressLint("NotifyDataSetChanged")
    private fun updateQuantity(
        productId: Long,
        quantity: Int,
    ) {
        productsIdCounts.find { it.productId == productId } ?: run {
            productsIdCounts = productsIdCounts + ProductIdsCount(productId, quantity)
            return
        }

        productsIdCounts =
            productsIdCounts.map {
                if (it.productId == productId) {
                    it.copy(quantity = quantity)
                } else {
                    it
                }
            }
    }

    companion object {
        private const val TAG = "ProductRecyclerViewAdapter"
    }
}
