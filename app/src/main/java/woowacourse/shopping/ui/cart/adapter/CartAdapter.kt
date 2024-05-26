package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

class CartAdapter(
    private val viewModel: CartViewModel,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val productWithQuantities: MutableList<ProductWithQuantity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(
            binding,
            viewModel,
        )
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(productWithQuantities[position])
    }

    override fun getItemCount(): Int = productWithQuantities.size

    fun setData(items: List<ProductWithQuantity>) {
        addItems(items)
        if (productWithQuantities.size != CartViewModel.PAGE_SIZE) {
            notifyItemRangeRemoved(
                productWithQuantities.size + OFFSET,
                CartViewModel.PAGE_SIZE - productWithQuantities.size,
            )
        }
        notifyItemRangeChanged(DEFAULT_POSITION, productWithQuantities.size)
    }

    private fun addItems(items: List<ProductWithQuantity>) {
        productWithQuantities.apply {
            clear()
            addAll(items)
        }
    }

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val OFFSET = 1
    }
}
