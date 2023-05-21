package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.uimodel.CartProductUIModel

class ShoppingCartAdapter(
    private var cartProducts: List<CartProductUIModel>,
    private val onClickRemove: (CartProductUIModel) -> Unit,
    private val onClickCheckBox: (CartProductUIModel) -> Unit,
    private val onClickCountButton: (CartProductUIModel, TextView) -> Unit
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {

        return ShoppingCartViewHolder(parent, onClickRemove, onClickCheckBox, onClickCountButton)
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    fun update(updatedCartProducts: List<CartProductUIModel>) {
        cartProducts = updatedCartProducts
        notifyDataSetChanged()
    }
}
