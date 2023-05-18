package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.model.CartProductUiModel

class CartRecyclerAdapter(
    private var shoppingCartProducts: List<CartProductUiModel>,
    private val cartProductCountPickerListener: CartProductCountPickerListener,
    private val onProductSelectingChanged: (product: CartProductUiModel, isSelected: Boolean) -> Unit,
    private val onShoppingCartProductRemoved: (product: CartProductUiModel) -> Unit,
    private val onTotalPriceChanged: () -> Unit,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    init {
        onTotalPriceChanged()
    }

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

    @SuppressLint("NotifyDataSetChanged")
    fun refreshItems(products: List<CartProductUiModel>) {
        shoppingCartProducts = products
        notifyDataSetChanged()
        onTotalPriceChanged()
    }

    private fun getCountPickerListenerImpl(product: CartProductUiModel) =
        object : CountPickerListener {

            override fun onPlus() {
                cartProductCountPickerListener.onPlus(product)
            }

            override fun onMinus() {
                cartProductCountPickerListener.onMinus(product)
            }
        }

    override fun getItemCount(): Int = shoppingCartProducts.size
}
