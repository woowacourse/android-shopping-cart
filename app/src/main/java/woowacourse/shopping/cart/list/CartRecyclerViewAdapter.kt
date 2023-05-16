package woowacourse.shopping.cart.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.CartActivity.Companion.CART_UNIT_SIZE
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.CartUIModel

class CartRecyclerViewAdapter(
    private var cartProducts: List<CartProductUIModel>,
    private val cartRepository: CartRepository,
    private val onClickRemove: (CartProductUIModel, Int) -> Unit
) : RecyclerView.Adapter<CartRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductInCartBinding.inflate(inflater, parent, false)
        return CartRecyclerViewHolder(
            view,
            CartUIModel(cartRepository.getAll().take(5)),
            onClickRemove
        )
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: CartRecyclerViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    fun remove(cartProductUIModel: CartProductUIModel) {
        cartRepository.remove(cartProductUIModel)
    }

    fun changePage(page: Int) {
        val size = cartRepository.getSize()
        val unitSize = if (size / CART_UNIT_SIZE < page) size % CART_UNIT_SIZE else CART_UNIT_SIZE
        cartProducts = cartRepository.getUnitData(unitSize, page)
        notifyDataSetChanged()
    }
}
