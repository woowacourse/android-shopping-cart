package woowacourse.shopping.shoppingcart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartItemViewHolder private constructor(
    private val binding: ItemShoppingCartBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var shoppingCartProduct: ShoppingCartProductUiModel

    fun setOnClicked(
        onRemoveClicked: (ShoppingCartProductUiModel) -> Unit,
        onPlusImageClicked: (product: ShoppingCartProductUiModel) -> Unit,
        onMinusImageClicked: (product: ShoppingCartProductUiModel) -> Unit,
    ) {
        with(binding) {
            imageRemoveProduct.setOnClickListener {
                onRemoveClicked(shoppingCartProduct)
            }
            imagePlus.setOnClickListener {
                Log.d("WOOGI", "setOnClicked: PLUS")
                onPlusImageClicked(shoppingCartProduct)
            }
            imageMinus.setOnClickListener {
                Log.d("WOOGI", "setOnClicked: MINUS")
                onMinusImageClicked(shoppingCartProduct)
            }
        }
    }

    fun bind(
        product: ShoppingCartProductUiModel,
    ) {
        shoppingCartProduct = product

        with(binding) {
            Glide.with(root.context)
                .load(shoppingCartProduct.imageUrl)
                .into(imageProduct)

            textProductCount.text = shoppingCartProduct.count.toString()
            textProductName.text = shoppingCartProduct.name
            textProductPrice.text = shoppingCartProduct.price.toString()
            checkBoxSelecting.isChecked = shoppingCartProduct.selected
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingCartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return ShoppingCartItemViewHolder(binding)
        }
    }
}
