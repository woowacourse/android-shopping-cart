package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.model.UiPageNumber
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.cart.CartContract.View
import woowacourse.shopping.ui.cart.listener.CartClickListener
import woowacourse.shopping.ui.cart.recyclerview.adapter.CartAdapter
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.extension.showToast
import woowacourse.shopping.util.inject.inject

class CartActivity : AppCompatActivity(), View, CartClickListener {
    private val presenter: CartPresenter by lazy { inject(this, this) }
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater).setContentView(this)
        binding.lifecycleOwner = this
        binding.presenter = presenter
        binding.adapter = CartAdapter(this)
        presenter.fetchCart(1)
    }

    override fun updateCart(cartProducts: List<UiCartProduct>) {
        binding.adapter?.submitList(cartProducts)
    }

    override fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean) {
        binding.previousButton.isEnabled = previousEnabled
        binding.nextButton.isEnabled = nextEnabled
    }

    override fun updatePageNumber(page: UiPageNumber) {
        binding.pageNumberTextView.text = page.toText()
    }

    override fun updateTotalPrice(totalPrice: Int) {
        binding.totalPriceTextView.text = getString(R.string.price_format, totalPrice)
    }

    override fun onCountChanged(product: UiProduct, count: Int, isIncreased: Boolean) {
        presenter.changeProductCount(product, count, isIncreased)
    }

    override fun onCheckStateChanged(product: UiProduct, isChecked: Boolean) {
        presenter.changeProductSelectState(product, isChecked)
    }

    override fun onDeleteClick(product: UiProduct) {
        presenter.removeProduct(product)
    }

    override fun showOrderComplete(productCount: Int) {
        showToast(getString(R.string.order_success_message, productCount))
        navigateToHome()
    }

    override fun showOrderFailed() {
        showToast(getString(R.string.order_failed_message))
    }

    override fun navigateToHome() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
