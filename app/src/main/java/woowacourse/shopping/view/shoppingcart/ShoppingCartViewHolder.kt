package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartViewHolder(
    parent: ViewGroup,
    private val onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
) {
    private val binding = ItemCartProductBinding.bind(itemView)
    private lateinit var cartProduct: CartProductUIModel

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProduct.productUIModel)
        }
    }

    fun bind(item: CartProductUIModel) {
        binding.cartProduct = cartProduct
    }
}
