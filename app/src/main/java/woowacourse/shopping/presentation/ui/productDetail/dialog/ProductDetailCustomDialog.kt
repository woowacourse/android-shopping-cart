package woowacourse.shopping.presentation.ui.productDetail.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.LayoutProductDetailDialogBinding
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.productDetail.dialog.presenter.ProductDetailDialogContract
import woowacourse.shopping.presentation.ui.productDetail.dialog.presenter.ProductDetailDialogPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailCustomDialog(val context: Context) : ProductDetailDialogContract.View {
    private lateinit var binding: LayoutProductDetailDialogBinding
    private val dialog by lazy { Dialog(context) }
    private lateinit var inflater: LayoutInflater
    override val presenter: ProductDetailDialogContract.Presenter by lazy {
        ProductDetailDialogPresenter(
            this,
            ShoppingCartRepositoryImpl(
                shoppingCartDataSource = ShoppingCartDao(context),
                productDataSource = ProductDao(context),
            ),
        )
    }

    init {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(context)
        }
    }

    override fun setCount(product: ProductInCartUiState) {
        onBindData(product)
    }

    fun onCreate(product: ProductInCartUiState) {
        binding = LayoutProductDetailDialogBinding.inflate(inflater)
        dialog.apply {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
        }

        onBindData(product)
        setClickEvent()
        dialog.show()
    }

    private fun setClickEvent() {
        setClickEventOnToShoppingCartButton()
        setClickEventOnSelectButton()
    }

    private fun setClickEventOnToShoppingCartButton() {
        binding.setClickListener = object : ProductDetailDialogClickListener {

            override fun setClickEventOnOperatorButton(
                operator: Boolean,
                productInCart: ProductInCartUiState,
            ) {
                val request = if (operator) Operator.PLUS else Operator.MINUS
                if (checkCountUnderMinimum(productInCart, operator)) return
                presenter.addCountOfProductInCart(request, productInCart)
            }

            override fun setClickEventOnToShoppingCart(product: ProductInCartUiState) {
                presenter.addProductInCart(product)
                navigateToShoppingCartActivity()
            }
        }
    }

    private fun checkCountUnderMinimum(
        productInCart: ProductInCartUiState,
        operator: Boolean,
    ): Boolean {
        if (productInCart.quantity == MINIMUM && !operator) {
            return true
        }
        return false
    }

    private fun navigateToShoppingCartActivity() {
        val intent = ShoppingCartActivity.getIntent(context)
        context.apply {
            startActivity(intent)
        }
        dialog.dismiss()
    }

    private fun onBindData(product: ProductInCartUiState) {
        binding.product = product
    }

    private fun setClickEventOnSelectButton() {
        binding.btnDetailDialogSelect.setOnClickListener {
            dialog.dismiss()
        }
    }

    companion object {
        private const val MINIMUM = 1
    }
}
