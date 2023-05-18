package woowacourse.shopping.ui.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiPageNumber
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View
import woowacourse.shopping.ui.basket.recyclerview.adapter.BasketAdapter
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.inject.injectBasketPresenter

class BasketActivity : AppCompatActivity(), View {
    override val presenter: Presenter by lazy { injectBasketPresenter(this, this) }
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater).setContentView(this)
        binding.presenter = presenter
        binding.adapter = BasketAdapter(presenter::deleteBasketProduct)
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

    override fun closeScreen() {
        finish()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
