package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductAdapter(
    private var cartProducts: List<CartProductModel>,
    private val onProductItemClick: (CartProductModel) -> Unit,
    private val onMinusClick: (CartProductModel) -> Unit,
    private val onPlusClick: (CartProductModel) -> Unit,
    private val onCartAddClick: (CartProductModel) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onProductItemClick, onMinusClick, onPlusClick, onCartAddClick
        )
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    fun updateProducts(newCartProducts: List<CartProductModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = cartProducts.size

            override fun getNewListSize(): Int = newCartProducts.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                cartProducts[oldItemPosition] == newCartProducts[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                cartProducts[oldItemPosition] == newCartProducts[newItemPosition]
        })

        cartProducts = newCartProducts
        result.dispatchUpdatesTo(this@ProductAdapter)
    }

    fun addProducts(cartProducts: List<CartProductModel>) {
        val lastPosition = itemCount
        this.cartProducts += cartProducts
        notifyItemRangeInserted(lastPosition, cartProducts.size)
    }
}
