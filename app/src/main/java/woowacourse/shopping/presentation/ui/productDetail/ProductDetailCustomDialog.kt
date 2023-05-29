package woowacourse.shopping.presentation.ui.productDetail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import woowacourse.shopping.databinding.LayoutProductDetailDialogBinding
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailCustomDialog(val context: Context) {
    private lateinit var binding: LayoutProductDetailDialogBinding
    private val dialog by lazy { Dialog(context) }
    private lateinit var inflater: LayoutInflater

    init {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(context)
        }
    }

    fun onCreate(product: ProductInCartUiState, onClick: ProductDetailClickListener) {
        binding = LayoutProductDetailDialogBinding.inflate(inflater)
        dialog.apply {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(false)
            setContentView(binding.root)
        }

        initView(product, onClick)
        setClickEventOnSelectButton()
        dialog.show()
    }

    private fun initView(product: ProductInCartUiState, onClick: ProductDetailClickListener) {
        binding.product = product
        binding.setClickListener = onClick
    }

    private fun setClickEventOnSelectButton() {
        binding.btnDetailDialogSelect.setOnClickListener {
            dialog.dismiss()
        }
    }
}
