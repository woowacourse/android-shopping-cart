package woowacourse.shopping.ui.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.model.UiPageNumber
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View
import woowacourse.shopping.ui.basket.recyclerview.adapter.BasketAdapter
import woowacourse.shopping.util.factory.createBasketPresenter

class BasketActivity : AppCompatActivity(), View {
    private val shoppingDatabase by lazy { ShoppingDatabase(this) }
    override val presenter: Presenter by lazy { createBasketPresenter(this, shoppingDatabase) }
    private lateinit var binding: ActivityBasketBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket)
        binding.presenter = presenter
        binding.adapter = BasketAdapter(presenter::removeBasketProduct)
    }

    override fun updateBasket(products: List<UiProduct>) {
        binding.adapter?.submitList(products)
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

    override fun onDestroy() {
        super.onDestroy()
        shoppingDatabase.close()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
