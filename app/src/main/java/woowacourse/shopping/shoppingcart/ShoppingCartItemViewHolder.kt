package woowacourse.shopping.shoppingcart

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
        onRemoveClicked: (product: ShoppingCartProductUiModel) -> Unit,
        productCountPickerListener: ShoppingCartProductCountPicker,
        onSelectingChanged: (product: ShoppingCartProductUiModel, isSelected: Boolean) -> Unit,
    ) {
        with(binding) {
            imageRemoveProduct.setOnClickListener {
                onRemoveClicked(shoppingCartProduct)
            }
            imagePlus.setOnClickListener {
                productCountPickerListener.onPlus(shoppingCartProduct)
            }
            imageMinus.setOnClickListener {
                productCountPickerListener.onMinus(shoppingCartProduct)
            }
            checkBoxSelecting.setOnCheckedChangeListener { _, isChecked ->
                onSelectingChanged(
                    shoppingCartProduct,
                    isChecked
                )
            }
        }
    }

    // TODO: 바인딩을 생성할 때 해줘버리면
    // TODO: 고차함수 바인딩은 어떻게 넘겨줘야할까
    // TODO: 모델 가지고 있기가 싫다
    fun bind(
        product: ShoppingCartProductUiModel,
    ) {
        shoppingCartProduct = product
        binding.product = shoppingCartProduct

        with(binding) {
            Glide.with(root.context)
                .load(shoppingCartProduct.imageUrl)
                .into(imageProduct)
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
