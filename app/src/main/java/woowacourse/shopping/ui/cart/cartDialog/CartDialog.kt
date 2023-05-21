package woowacourse.shopping.ui.cart.cartDialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutCartDialogBinding

class CartDialog(context: Context, title: String, price: Int, addToCart: (Int) -> Unit) :
    Dialog(context) {
    private val binding: LayoutCartDialogBinding = DataBindingUtil
        .inflate(LayoutInflater.from(context), R.layout.layout_cart_dialog, null, false)

    init {
        setContentView(binding.root)

        binding.productName = title
        binding.productPrice = price
        binding.addToCartListener = {
            dismiss()
            addToCart(binding.cvProductCounter.count)
        }
    }
}
