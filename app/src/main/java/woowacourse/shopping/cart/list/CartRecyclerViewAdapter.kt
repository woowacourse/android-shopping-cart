package woowacourse.shopping.cart.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.cart.OnCheckedChangedListener
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.CartUIModel

class CartRecyclerViewAdapter(
    private val onClickProduct: ProductClickListener,
    private val onClickRemove: (CartProductUIModel, Int) -> Unit,
    private val onChangedCheckBox: OnCheckedChangedListener,
) : RecyclerView.Adapter<CartRecyclerViewHolder>() {
    private val cartProducts = mutableListOf<CartProductUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewHolder {
        return CartRecyclerViewHolder(
            CartRecyclerViewHolder.getView(parent),
            CartUIModel(cartProducts.take(CART_PRODUCT_UNIT_SIZE)),
            onClickProduct,
            onClickRemove,
            onChangedCheckBox,
        )
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: CartRecyclerViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    fun initProducts(newProducts: List<CartProductUIModel>) {
        cartProducts.clear()
        cartProducts.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun removeData(cartProductUIModel: CartProductUIModel) {
        cartProducts.remove(cartProductUIModel)
    }

    companion object {
        private const val CART_PRODUCT_UNIT_SIZE = 5
    }
}
