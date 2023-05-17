package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartRecyclerAdapter(
    private var shoppingCartProducts: List<ShoppingCartProductUiModel>,
    private val shoppingCartProductCountPicker: ShoppingCartProductCountPicker,
    private val onProductSelectingChanged: (product: ShoppingCartProductUiModel, isSelected: Boolean) -> Unit,
    private val onShoppingCartProductRemoved: (product: ShoppingCartProductUiModel) -> Unit,
    private val onTotalPriceChanged: () -> Unit,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {

    init {
        onTotalPriceChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {

        return ShoppingCartItemViewHolder.from(parent).apply {
            setOnClicked(
                onRemoveClicked = onShoppingCartProductRemoved,
                productCountPickerListener = shoppingCartProductCountPicker,
                onSelectingChanged = onProductSelectingChanged,
            )
        }
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(
            product = shoppingCartProducts[position],
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshItems(products: List<ShoppingCartProductUiModel>) {
        shoppingCartProducts = products
        notifyDataSetChanged()
        onTotalPriceChanged()
    }

    override fun getItemCount(): Int = shoppingCartProducts.size
}
