package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartAdapter(
    private var checkableCartProducts: List<CheckableCartProductModel>,
    private val onCartItemRemoveButtonClick: (CartProductModel) -> Unit,
    private val onMinusClick: (CartProductModel) -> Unit,
    private val onPlusClick: (CartProductModel) -> Unit,
    private val onCheck: (CheckableCartProductModel, Boolean) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onCartItemRemoveButtonClick, onMinusClick, onPlusClick, onCheck
        )
    }

    override fun getItemCount(): Int = checkableCartProducts.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(checkableCartProducts[position])
    }

    override fun getItemViewType(position: Int): Int = checkableCartProducts.size

    fun updateCartProducts(newCheckableCartProducts: List<CheckableCartProductModel>) {
        val result = DiffUtil.calculateDiff(
            CartDiffUtilCallback(
                checkableCartProducts, newCheckableCartProducts
            )
        )

        checkableCartProducts = newCheckableCartProducts
        result.dispatchUpdatesTo(this@CartAdapter)
    }
}
