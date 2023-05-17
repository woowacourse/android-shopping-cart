package woowacourse.shopping.ui.cart.cartDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutCartDialogBinding

@SuppressLint("SetTextI18n")
class CartDialog(context: Context, title: String, price: Int, addToCart: (Int) -> Unit) :
    Dialog(context) {
    private val binding: LayoutCartDialogBinding = DataBindingUtil
        .inflate(LayoutInflater.from(context), R.layout.layout_cart_dialog, null, false)

    private var count: Int
        get() = binding.tvProductCount.text.toString().toInt()
        set(value) { binding.tvProductCount.text = value.toString() }
    init {
        setContentView(binding.root)

        binding.productName = title
        binding.productPrice = price
        binding.addToCartListener = {
            dismiss()
            addToCart(count)
        }
        count = 1
        binding.tvProductCountPlus.setOnClickListener { count += 1 }
        binding.tvProductCountMinus.setOnClickListener { count -= 1 }
    }
}
