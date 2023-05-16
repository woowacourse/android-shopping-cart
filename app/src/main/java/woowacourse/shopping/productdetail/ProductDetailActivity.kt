package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
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

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    override lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)

        val productData =
            intent.getSerializableCompat(BundleKeys.KEY_PRODUCT) ?: ProductUIModel.dummy
        val recentRepository =
            RecentProductDBRepository(RecentProductDBHelper(this).writableDatabase)
        val cartRepository = CartDBRepository(CartDBHelper(this).writableDatabase)

        presenter = ProductDetailPresenter(this, productData, recentRepository, cartRepository)
        presenter.insertRecentRepository(System.currentTimeMillis())

        presenter.initPage()
    }

    override fun setViews(productData: ProductUIModel) {
        Glide.with(binding.root.context).load(productData.imageUrl).into(binding.ivProductImage)
        binding.tvProductName.text = productData.name
        binding.tvPrice.text = productData.price.toString()
        binding.btAddToCart.setOnClickListener {
            presenter.onClickAddToCart()
        }
    }

    override fun showCartPage() {
        startActivity(CartActivity.intent(binding.root.context))
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
        fun intent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
