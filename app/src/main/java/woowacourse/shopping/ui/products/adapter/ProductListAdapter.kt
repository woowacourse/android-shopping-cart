package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.listener.ProductItemListener
import woowacourse.shopping.ui.cart.uistate.CartUIState
import woowacourse.shopping.ui.products.uistate.ProductUIState

class ProductListAdapter(
    private val products: MutableList<ProductUIState>,
    private val productListener: ProductItemListener,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false,
        )

        return ProductListViewHolder(
            ItemProductBinding.bind(view),
            productListener,
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun addItems(newProducts: List<ProductUIState>) {
        products.addAll(newProducts)
    }

    fun updateCount(productId: Long, count: Int) {
        products.find { it.id == productId }?.let {
            it.updateCount(count)
            notifyItemChanged(products.indexOf(it))
        }
    }

    fun deleteCount(productId: Long) {
        products.find { it.id == productId }?.let {
            it.updateCount()
            notifyItemChanged(products.indexOf(it))
        }
    }

    fun notifyCountUpdated(cartProducts: List<CartUIState>) {
        products.forEach { product ->
            val cartProduct: CartUIState? = cartProducts.find { it.id == product.id }
            updateDeletedItemCount(cartProduct, product)
            updateChangedItemCount(cartProduct, product)
        }
    }

    private fun updateDeletedItemCount(cartProduct: CartUIState?, product: ProductUIState) {
        if (cartProduct == null && product.count > 0) {
            deleteCount(product.id)
            notifyItemChanged(products.indexOf(product))
        }
    }

    private fun updateChangedItemCount(cartProduct: CartUIState?, product: ProductUIState) {
        if (cartProduct == null) return

        if (product.count != cartProduct.count) {
            updateCount(product.id, cartProduct.count)
            notifyItemChanged(products.indexOf(product))
        }
    }

    class ProductListViewHolder(
        private val binding: ItemProductBinding,
        productListener: ProductItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.listener = productListener
        }

        fun bind(product: ProductUIState) {
            binding.product = product
            setCountButtonVisibility(product.count != ProductUIState.NO_COUNT)
        }

        private fun setCountButtonVisibility(isCountChanging: Boolean) {
            binding.btnProductAdd.isVisible = !isCountChanging
            binding.btnProductPlusCount.isVisible = isCountChanging
            binding.btnProductMinusCount.isVisible = isCountChanging
            binding.tvProductCount.isVisible = isCountChanging
        }
    }
}
