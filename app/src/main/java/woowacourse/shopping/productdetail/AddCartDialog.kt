package woowacourse.shopping.productdetail

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutAddCartDialogBinding
import woowacourse.shopping.productcatalogue.ProductCountClickListener
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.view.CounterView

class AddCartDialog(
    context: Context,
    cartProductUIModel: CartProductUIModel,
    addToCart: (Int) -> Unit
) : Dialog(context) {
    private val binding: LayoutAddCartDialogBinding = DataBindingUtil
        .inflate(LayoutInflater.from(context), R.layout.layout_add_cart_dialog, null, false)

    init {
        setContentView(binding.root)

        binding.cartProduct = cartProductUIModel

        binding.addToCartListener = object : AddToCartListener {
            override fun onClick(counterView: CounterView) {
                dismiss()
                addToCart(counterView.count)
            }
        }
        binding.counterView.count = 1
        binding.productCountClickListener = object : ProductCountClickListener {
            override fun onDownClicked(cartProduct: CartProductUIModel, countView: TextView) {
                val count = countView.text.toString().toInt() - 1
                if (count <= 0) return
                countView.text = count.toString()
            }

            override fun onUpClicked(cartProduct: CartProductUIModel, countView: TextView) {
                val count = countView.text.toString().toInt() + 1
                if (count > 99) return
                countView.text = count.toString()
            }
        }
    }
}
