package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.CartProductDao
import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    override lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var product: ProductUIModel
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)
        setPresenter()
        setProductDetailView()
        saveRecentProduct()
        setAddToCartClick()
    }

    private fun setPresenter() {
        product = intent.getSerializableCompat(BundleKeys.KEY_PRODUCT)
            ?: throw IllegalStateException(NON_FOUND_KEY_ERROR)

        presenter =
            ProductDetailPresenter(
                this,
                product = product,
                recentProductsRepository = RecentProductRepositoryImpl(RecentProductDao(this))
            )
    }

    override fun setProductDetailView() {
        binding.product = presenter.product
        binding.loLatestRecent.visibility = GONE

        val depth = intent.getSerializableCompat<Int>(BundleKeys.KEY_DEPTH)
            ?: throw IllegalStateException(NON_FOUND_KEY_ERROR)
        if (presenter.isRecentProductExist() && depth == DEPTH_PARENT) {
            val recentProduct = presenter.setRecentProductView(product)
            binding.recentProduct = recentProduct
            binding.loLatestRecent.setOnClickListener {
                showDetailProduct(recentProduct)
            }
        }
    }

    override fun hideLatestProduct() {
        binding.loLatestRecent.visibility = GONE
    }
    override fun showLatestProduct() {
        binding.loLatestRecent.visibility = VISIBLE
    }

    private fun saveRecentProduct() {
        presenter.saveRecentProduct()
    }

    private fun setAddToCartClick() {
        binding.btnAddToCart.setOnClickListener {
            val dialog = CountSelectDialog(this, CartProductRepositoryImpl(CartProductDao(this)))
            presenter.showDialog(dialog)
        }
    }

    override fun showDetailProduct(recentProductUIModel: RecentProductUIModel) {
        intent.putExtra(BundleKeys.KEY_PRODUCT, recentProductUIModel.productUIModel)
        intent.putExtra(BundleKeys.KEY_DEPTH, DEPTH_CHILD)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val NON_FOUND_KEY_ERROR = "일치하는 키가 없습니다."
        private const val DEPTH_PARENT = 0
        private const val DEPTH_CHILD = 1
        fun intent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
