package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartRecyclerAdapter(
    private var shoppingCartProducts: List<ShoppingCartProductUiModel>,
    private val shoppingCartProductCountPicker: ShoppingCartProductCountPicker,
    private val onShoppingCartProductRemoved: (product: ShoppingCartProductUiModel) -> Unit,
    private val onTotalPriceChanged: (products: List<ShoppingCartProductUiModel>) -> Unit,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    init {
        onTotalPriceChanged(shoppingCartProducts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {

        return ShoppingCartItemViewHolder.from(parent).apply {
            setOnClicked(
                onRemoveClicked = ::removeItem,
                onPlusImageClicked = shoppingCartProductCountPicker::onPlus,
                onMinusImageClicked = shoppingCartProductCountPicker::onMinus
            )
        }
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(product = shoppingCartProducts[position])
    }

    fun removeItem(product: ShoppingCartProductUiModel) {
        onShoppingCartProductRemoved(product)
        notifyItemChanged(shoppingCartProducts.indexOfFirst { it == product })
        onTotalPriceChanged(shoppingCartProducts)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshItems(products: List<ShoppingCartProductUiModel>) {
        shoppingCartProducts = products.toMutableList()
        notifyDataSetChanged()
        onTotalPriceChanged(shoppingCartProducts)
    }

    override fun getItemCount(): Int = shoppingCartProducts.size
}
