package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.cart.uistate.CartUIState
import woowacourse.shopping.ui.products.uistate.ProductUIState

class ProductListAdapter(
    private val products: MutableList<ProductUIState>,
    private val onItemClick: (Long) -> Unit,
    private val onPlusCountButtonClick: (productId: Long, oldCount: Int) -> Unit,
    private val onMinusCountButtonClick: (productId: Long, oldCount: Int) -> Unit,
    private val onStartCountButtonClick: (product: ProductUIState) -> Unit,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    private val buttonClickListener: (option: Int, position: Int) -> Unit = { option, position ->
        when (option) {
            CLICK_ITEM -> onItemClick(products[position].id)
            CLICK_COUNT_PLUS -> {
                onPlusCountButtonClick(
                    products[position].id,
                    products[position].count,
                )
                notifyItemChanged(position)
            }

            CLICK_COUNT_MINUS -> {
                onMinusCountButtonClick(
                    products[position].id,
                    products[position].count,
                )
                notifyItemChanged(position)
            }

            CLICK_COUNT_START -> {
                onStartCountButtonClick(products[position])
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false,
        )

        return ProductListViewHolder(
            ItemProductBinding.bind(view),
            buttonClickListener,
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
        products.find { it.id == productId }?.updateCount(count)
    }

    fun deleteCount(productId: Long) {
        products.find { it.id == productId }?.updateCount()
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
        private val onButtonClick: (Int, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onButtonClick(CLICK_ITEM, position) }

            binding.btnProductAdd.setOnClickListener {
                onButtonClick(CLICK_COUNT_START, position)
            }

            binding.btnProductPlusCount.setOnClickListener {
                onButtonClick(CLICK_COUNT_PLUS, position)
            }
            binding.btnProductMinusCount.setOnClickListener {
                onButtonClick(CLICK_COUNT_MINUS, position)
            }
        }

        fun bind(product: ProductUIState) {
            binding.product = product

            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivProduct)

            binding.tvProductCount.text = product.count.toString()

            setCountButtonVisibility(product.count != ProductUIState.NO_COUNT)
        }

        private fun setCountButtonVisibility(isCountChanging: Boolean) {
            binding.btnProductAdd.isVisible = !isCountChanging
            binding.btnProductPlusCount.isVisible = isCountChanging
            binding.btnProductMinusCount.isVisible = isCountChanging
            binding.tvProductCount.isVisible = isCountChanging
        }
    }

    companion object {
        private const val CLICK_ITEM = 0
        private const val CLICK_COUNT_PLUS = 1
        private const val CLICK_COUNT_MINUS = 2
        private const val CLICK_COUNT_START = 3
    }
}
