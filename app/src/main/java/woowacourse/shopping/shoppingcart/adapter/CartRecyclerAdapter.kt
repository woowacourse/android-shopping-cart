package woowacourse.shopping.shoppingcart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.shoppingcart.CartProductDiffUtil
import woowacourse.shopping.shoppingcart.adapter.viewholder.CartItemViewHolder

class CartRecyclerAdapter(
    private var shoppingCartProducts: List<CartProductUiModel>,
    private val cartProductCountPickerListener: CartProductCountPickerListener,
    private val onProductSelectingChanged: (id: Int, isSelected: Boolean) -> Unit,
    private val onShoppingCartProductRemoved: (id: Int) -> Unit,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {

        return CartItemViewHolder.from(parent).apply {
            setOnClicked(
                onRemoveClicked = onShoppingCartProductRemoved,
                onSelectingChanged = onProductSelectingChanged,
            )
        }
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(
            product = shoppingCartProducts[position],
            getCountPickerListener = ::getCountPickerListenerImpl
        )
    }

    fun refreshItems(products: List<CartProductUiModel>) {
        val diffCartProducts = DiffUtil.calculateDiff(
            CartProductDiffUtil(
                oldCartProducts = shoppingCartProducts,
                newCartProducts = products
            )
        )

        shoppingCartProducts = products
        diffCartProducts.dispatchUpdatesTo(this@CartRecyclerAdapter)
    }

    private fun getCountPickerListenerImpl(id: Int) =
        object : CountPickerListener {

            override fun onPlus() {
                cartProductCountPickerListener.onPlus(id)
            }

            override fun onMinus() {
                cartProductCountPickerListener.onMinus(id)
            }
        }

    override fun getItemCount(): Int = shoppingCartProducts.size
}
