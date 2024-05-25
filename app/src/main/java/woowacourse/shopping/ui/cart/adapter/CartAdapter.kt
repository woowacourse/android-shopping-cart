package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartAdapter(
    private val onClickExit: OnClickExit,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val products: MutableList<ProductUiModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(
            products[position],
            onClickExit,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity,
        )
    }

    override fun getItemCount(): Int = products.size

    fun updateCartItems(updatedProducts: List<ProductUiModel>) {
        val newProducts = updatedProducts.subtract(products.toSet())
        if (newProducts.size == updatedProducts.size) {
            changeAllProduct(newProducts)
            return
        }

        if (products.size > updatedProducts.size) {
            val oldProducts = products.subtract(updatedProducts.toSet())
            oldProducts.forEach { deleteProduct(it) }
        }

        newProducts.forEach { changeProduct(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeAllProduct(newProducts: Set<ProductUiModel>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    private fun deleteProduct(oldProduct: ProductUiModel) {
        val position = findProductPosition(oldProduct.productId)
        products.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun changeProduct(newProduct: ProductUiModel) {
        val position = findProductPosition(newProduct.productId)
        products[position] = newProduct
        notifyItemChanged(position)
    }

    private fun findProductPosition(productId: Long): Int {
        return products.indexOfFirst { it.productId == productId }
    }
}
