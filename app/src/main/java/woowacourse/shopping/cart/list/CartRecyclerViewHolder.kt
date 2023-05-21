package woowacourse.shopping.cart.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.cart.ItemClickListener
import woowacourse.shopping.cart.OnCheckedChangedListener
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.CartUIModel

class CartRecyclerViewHolder(
    private val binding: ItemProductInCartBinding,
    cartProducts: CartUIModel,
    onClickProduct: ProductClickListener,
    itemClickListener: ItemClickListener,
    onCheckedChanged: OnCheckedChangedListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = onClickProduct

        binding.cartItemListener = itemClickListener

        binding.ivCancel.setOnClickListener {
            itemClickListener.clickDeleteButton(
                cartProducts.products[adapterPosition],
                adapterPosition
            )
        }

        binding.onCheckedChangeListener = onCheckedChanged
    }

    fun bind(cartProductUIModel: CartProductUIModel) {
        binding.cartProduct = cartProductUIModel
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductInCartBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemProductInCartBinding.inflate(inflater, parent, false)
        }
    }
}
