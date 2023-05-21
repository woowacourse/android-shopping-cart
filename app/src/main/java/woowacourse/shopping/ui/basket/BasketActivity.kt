package woowacourse.shopping.ui.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiPageNumber
import woowacourse.shopping.ui.basket.BasketContract.View
import woowacourse.shopping.ui.basket.recyclerview.adapter.BasketAdapter
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.extension.showToast
import woowacourse.shopping.util.inject.inject

class BasketActivity : AppCompatActivity(), View {
    private val presenter: BasketPresenter by lazy { inject(this, this) }
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater).setContentView(this)
        binding.lifecycleOwner = this
        binding.presenter = presenter
        binding.adapter = BasketAdapter(
            presenter::removeFromCart,
            presenter::selectProduct,
            presenter::unselectProduct,
            presenter::increaseProductCount,
            presenter::decreaseProductCount,
        )
        presenter.fetchBasket(1)
    }

    override fun updateBasket(basketProducts: List<UiBasketProduct>) {
        binding.adapter?.submitList(basketProducts)
    }

    override fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean) {
        binding.previousButton.isEnabled = previousEnabled
        binding.nextButton.isEnabled = nextEnabled
    }

    override fun updatePageNumber(page: UiPageNumber) {
        binding.pageNumberTextView.text = page.toText()
    }

    override fun showOrderComplete(productCount: Int) {
        showToast(getString(R.string.order_success_message, productCount))
    }

    override fun showOrderFailed() {
        showToast(getString(R.string.order_failed_message))
    }

    override fun navigateToHome() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
