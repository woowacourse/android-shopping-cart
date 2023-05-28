package woowacourse.shopping.view.productdetail

import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import woowacourse.shopping.databinding.CartDialogBinding
import woowacourse.shopping.model.ProductModel

class CartDialog(
    context: ProductDetailActivity,
    private val productModel: ProductModel,
    private val putInCart: (ProductModel, Int) -> Unit,
    private val navigateToNextStep: () -> Unit,
) {
    private val dialog = Dialog(context)
    private var binding: CartDialogBinding = CartDialogBinding.inflate(context.layoutInflater)
    private var productCount = DEFAULT_COUNT_VALUE

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        setBinding()
        setCountView()
        setClickListener()
    }

    fun show() {
        dialog.show()
    }

    private fun setBinding() {
        binding.product = productModel
    }

    private fun setCountView() {
        binding.dialogCount.count = productCount
        binding.dialogCount.plusClickListener = { plusCount() }
        binding.dialogCount.minusClickListener = { subCount() }
    }

    private fun setClickListener() {
        binding.dialogPutButton.setOnClickListener {
            putInCart(productModel, productCount)
            dialog.dismiss()
            navigateToNextStep()
        }
    }

    private fun plusCount() {
        productCount += COUNT_CALCULATE_VALUE
    }

    private fun subCount() {
        productCount -= COUNT_CALCULATE_VALUE
    }

    companion object {
        private const val DEFAULT_COUNT_VALUE = 1
        private const val COUNT_CALCULATE_VALUE = 1
    }
}
