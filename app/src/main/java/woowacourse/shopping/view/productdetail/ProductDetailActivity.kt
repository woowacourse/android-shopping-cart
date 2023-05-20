package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setPresenter()
        getData()
        bindView()
        showDialog()
        presenter.updateRecentViewedProducts()
    }

    private fun setBinding() {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
    }

    private fun setPresenter() {
        presenter =
            ProductDetailPresenter(this, CartDbRepository(this), RecentViewedDbRepository(this), ProductMockRepository)
    }

    override fun getData() {
        intent.getParcelableCompat<ProductModel>(PRODUCT)?.let { presenter.setProductData(it) }
    }

    private fun bindView() {
        binding.product = presenter.getProductData()
        binding.recentViewedProduct = presenter.getRecentViewedProductData()
        binding.presenter = presenter
    }

    private fun showDialog() {
        binding.btnPutInCart.setOnClickListener {
            val dialog = CartDialog(this, CartDbRepository(this))
            dialog.show(presenter.getProductData())
        }
    }

    override fun startCartActivity() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun showProductRecentViewedDetail(product: ProductModel) {
        val intent = newIntent(this, product)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.handleNextStep(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun handleBackButtonClicked() {
        finish()
    }

    companion object {
        const val PRODUCT = "PRODUCT"

        fun newIntent(context: Context, product: ProductModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT, product)
            return intent
        }
    }
}
