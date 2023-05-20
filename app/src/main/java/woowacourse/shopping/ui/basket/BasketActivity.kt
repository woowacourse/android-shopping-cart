package woowacourse.shopping.ui.basket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiPageNumber
import woowacourse.shopping.ui.basket.BasketContract.View
import woowacourse.shopping.ui.basket.recyclerview.adapter.BasketAdapter
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.inject.inject

class BasketActivity : AppCompatActivity(), View {
    override val presenter: BasketPresenter by lazy { inject(this, this) }
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater).setContentView(this)
        binding.lifecycleOwner = this
        binding.presenter = presenter
        binding.adapter = BasketAdapter(
            presenter::deleteBasketProduct,
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

    @SuppressLint("NotifyDataSetChanged")
    override fun updateAllCheckedState(basketProducts: List<UiBasketProduct>) {
        binding.adapter?.submitList(basketProducts)
        binding.adapter?.notifyDataSetChanged()
    }

    override fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean) {
        binding.previousButton.isEnabled = previousEnabled
        binding.nextButton.isEnabled = nextEnabled
    }

    override fun updatePageNumber(page: UiPageNumber) {
        binding.pageNumberTextView.text = page.toText()
    }

    override fun updateTotalPrice(price: Int) {
        binding.totalPriceTextView.text = getString(R.string.price_format, price)
    }

    override fun showOrderComplete(productCount: Int) {
        Toast.makeText(
            this,
            getString(R.string.order_success_message, productCount),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showOrderFailed() {
        Toast.makeText(this, getString(R.string.order_failed_message), Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
