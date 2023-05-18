package woowacourse.shopping.ui.productcounter

import android.app.Dialog
import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CounterBinding
import woowacourse.shopping.model.UiProduct

class ProductCounterDialog(
    context: Context,
    product: UiProduct,
    onPutInBasket: (Int) -> Unit,
) : Dialog(context) {

    init {
        val binding: CounterBinding = CounterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialogSize(context)
        binding.product = product
        binding.onPutInBasket = { count ->
            onPutInBasket(count)
            dismiss()
        }
    }

    private fun initDialogSize(context: Context) {
        val metrics = context.resources.displayMetrics
        val width = (metrics.widthPixels * 0.9).toInt()
        val height = (width * 0.4).toInt()
        window?.setLayout(width, height)
        window?.setBackgroundDrawableResource(R.drawable.shape_woowa_round_4_white_rect)
    }
}
