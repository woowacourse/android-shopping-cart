package woowacourse.shopping.presentation.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.util.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        supportActionBar?.title = ""

        val productId = intent.getLongExtra(KEY_PRODUCT_ID, -1)

        presenter.loadProductInfoById(productId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setProductInfoView(productModel: ProductModel) {
        Glide.with(this)
            .load(productModel.imageUrl)
            .into(binding.ivProductDetailThumbnail)
        binding.tvProductDetailTitle.text = productModel.title
        binding.tvProductDetailPrice.text = productModel.price.toString()
    }

    override fun handleErrorView() {
        showToast(getString(R.string.toast_message_system_error))
        finish()
    }

    companion object {
        private const val KEY_PRODUCT_ID = "KEY_PRODUCT_ID"
        internal fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(KEY_PRODUCT_ID, id)

            return intent
        }
    }
}
