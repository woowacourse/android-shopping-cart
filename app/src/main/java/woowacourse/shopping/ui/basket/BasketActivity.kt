package woowacourse.shopping.ui.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepository
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View
import woowacourse.shopping.ui.basket.recyclerview.adapter.BasketAdapter

class BasketActivity : AppCompatActivity(), View {
    override val presenter: Presenter by lazy {
        BasketPresenter(
            this,
            BasketRepository(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this))))
        )
    }
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket)
        binding.presenter = presenter
        binding.adapter = BasketAdapter(presenter::removeBasketProduct)
        setClickListener()
    }

    private fun setClickListener() {
        binding.tbBasket.setNavigationOnClickListener { finish() }
        binding.btnPrevious.setOnClickListener { presenter.fetchBasket(isNext = false) }
        binding.btnNext.setOnClickListener { presenter.fetchBasket(isNext = true) }
    }

    override fun updateBasket(products: List<UiProduct>) {
        binding.adapter?.submitList(products)
    }

    override fun updateNavigatorEnabled(previous: Boolean, next: Boolean) {
        binding.btnPrevious.isEnabled = previous
        binding.btnNext.isEnabled = next
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
