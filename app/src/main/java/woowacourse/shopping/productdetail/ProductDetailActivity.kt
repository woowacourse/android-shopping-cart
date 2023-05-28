package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shopping.domain.CartRepository
import com.shopping.domain.RecentRepository
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartDBRepository
import woowacourse.shopping.datas.RecentProductDBHelper
import woowacourse.shopping.datas.RecentProductDBRepository
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)

        val productData =
            intent.getSerializableCompat(BundleKeys.KEY_PRODUCT) ?: ProductUIModel.dummy
        val recentRepository: RecentRepository =
            RecentProductDBRepository(RecentProductDBHelper(this).writableDatabase)
        val cartRepository: CartRepository = CartDBRepository(CartDBHelper(this).writableDatabase)

        presenter = ProductDetailPresenter(this, productData, recentRepository, cartRepository)
        binding.presenter = presenter
        presenter.getMostRecentProduct()
        presenter.insertRecentRepository(System.currentTimeMillis())
    }

    override fun showCartPage() {
        startActivity(CartActivity.getIntent(binding.root.context))
    }

    override fun setLatestProductVisibility() {
        binding.clRecentProductBox.visibility = View.VISIBLE
    }

    override fun showRecentProduct(recentProduct: RecentProductUIModel) {
        binding.recentProduct = recentProduct
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
        fun getIntent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
