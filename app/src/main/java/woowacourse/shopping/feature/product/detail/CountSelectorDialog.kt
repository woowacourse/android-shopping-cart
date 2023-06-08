package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.feature.cart.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.feature.product.model.ProductState

class CountSelectorDialog(
    context: Context,
    plusCount: () -> Unit,
    minusCount: () -> Unit,
    addCartProduct: (count: Int) -> Unit
) {
    private val selectCountDialogBinding: DialogSelectCountBinding =
        DialogSelectCountBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context).apply {
        setView(selectCountDialogBinding.root)
        selectCountDialogBinding.counterView.count = MIN_COUNT_VALUE
        selectCountDialogBinding.counterView.plusClickListener = { plusCount() }
        selectCountDialogBinding.counterView.minusClickListener = { minusCount() }
        selectCountDialogBinding.addToCartBtn.setOnClickListener {
            addCartProduct(selectCountDialogBinding.counterView.count)
        }
    }.create()

    fun setCount(count: Int) {
        selectCountDialogBinding.counterView.count = count
    }

    fun show(productState: ProductState) {
        selectCountDialogBinding.product = productState
        dialog.dismiss()
        dialog.show()
    }
}
