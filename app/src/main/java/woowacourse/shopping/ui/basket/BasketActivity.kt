package woowacourse.shopping.ui.basket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepository
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.ui.model.UiProduct

class BasketActivity : AppCompatActivity(), BasketContract.View {
    override lateinit var presenter: BasketContract.Presenter

    private lateinit var binding: ActivityBasketBinding
    private lateinit var basketAdapter: BasketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket)
        initPresenter()
        initAdapter()
    }

    private fun initPresenter() {
        presenter = BasketPresenter(
            this,
            BasketRepository(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this))))
        )
    }

    private fun initAdapter() {
        basketAdapter = BasketAdapter { }
        binding.rvBasket.adapter = basketAdapter
        presenter.fetchBasketProducts()
    }

    override fun updateBasketProducts(products: List<UiProduct>) {
        basketAdapter.submitList(products)
    }
}
