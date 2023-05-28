package woowacourse.shopping.presentation.view.productdetail

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import woowacourse.shopping.databinding.LayoutCartDialogViewBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartInsertionDialog(
    context: Context,
    product: ProductModel,
    private val onAddClick: (Int) -> Unit
) : AlertDialog.Builder(context) {
    private val binding = LayoutCartDialogViewBinding.inflate(LayoutInflater.from(context))
    private val dialog: AlertDialog

    init {
        setView(binding.root)
        initCountView()
        setProductItemView(product)
        setAddButtonView()

        dialog = show()
    }

    private fun initCountView() {
        binding.countViewCartDialog.setMinCount(1)
        binding.countViewCartDialog.updateCount(1)
    }

    private fun setProductItemView(product: ProductModel) {
        binding.product = product
    }

    private fun setAddButtonView() {
        binding.btCartDialogAdd.setOnClickListener {
            onAddClick(getProductCount())
            dialog.dismiss()
        }
    }

    private fun getProductCount(): Int = binding.countViewCartDialog.getCount()
}
