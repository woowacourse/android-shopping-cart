package woowacourse.shopping.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.sql.cart.CartDao
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.util.keyError

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var presenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val product = intent.getParcelableCompat<ProductUiModel>(PRODUCT_KEY)
            ?: return keyError(PRODUCT_KEY)
        presenter = DetailPresenter(this, CartRepositoryImpl(CartDao(this)), product)
        binding.presenter = presenter
    }

    override fun showCartScreen() = startActivity(CartActivity.getIntent(this))

    override fun exitDetailScreen() = finish()

    companion object {
        private const val PRODUCT_KEY = "PRODUCT_KEY"
        fun getIntent(context: Context, product: ProductUiModel): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PRODUCT_KEY, product)

            return intent
        }
    }
}
