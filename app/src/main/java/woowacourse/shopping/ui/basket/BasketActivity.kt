package woowacourse.shopping.ui.basket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.local.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepositoryImpl
import woowacourse.shopping.databinding.ActivityBasketBinding
import woowacourse.shopping.ui.model.UiBasketProduct
import woowacourse.shopping.ui.shopping.ShoppingActivity

class BasketActivity : AppCompatActivity(), BasketContract.View {
    private lateinit var presenter: BasketContract.Presenter

    private lateinit var binding: ActivityBasketBinding
    private lateinit var basketAdapter: BasketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetResult()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket)
        initPresenter()
        initAdapter()
        initToolbarBackButton()
        navigatorClickListener()
    }

    private fun initSetResult() {
        setResult(Activity.RESULT_OK, ShoppingActivity.getResultIntent())
    }

    private fun initPresenter() {
        presenter = BasketPresenter(
            this,
            BasketRepositoryImpl(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this))))
        )
    }

    private fun initAdapter() {
        basketAdapter = BasketAdapter(presenter::removeBasketProduct)
        binding.rvBasket.adapter = basketAdapter
        presenter.initBasketProducts()
    }

    private fun initToolbarBackButton() {
        binding.tbBasket.setNavigationOnClickListener {
            finish()
        }
    }

    private fun navigatorClickListener() {
        binding.btnPrevious.setOnClickListener {
            presenter.updatePreviousPage()
        }
        binding.btnNext.setOnClickListener {
            presenter.updateNextPage()
        }
    }

    override fun updateBasketProducts(products: List<UiBasketProduct>) {
        basketAdapter.submitList(products)
    }

    override fun updateNavigatorEnabled(previous: Boolean, next: Boolean) {
        binding.btnPrevious.isEnabled = previous
        binding.btnNext.isEnabled = next
    }

    override fun updateCurrentPage(currentPage: Int) {
        binding.tvCurrentPage.text = currentPage.toString()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasketActivity::class.java)
    }
}
