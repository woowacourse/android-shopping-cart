package woowacourse.shopping.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.sql.cart.CartDao
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.databinding.DialogSelectCountBinding
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

        initRecentProduct()
    }

    private fun initRecentProduct() {
        val recentProduct = intent.getParcelableCompat<ProductUiModel>(RECENT_PRODUCT_KEY)
        if (recentProduct == null) {
            binding.mostRecentLayout.visibility = View.GONE
            return
        } else {
            binding.mostRecentTitle.text = recentProduct.name
            binding.mostRecentPrice.text =
                resources.getString(R.string.price_format).format(recentProduct.price)
            binding.mostRecentLayout.setOnClickListener {
                showProductDetail(recentProduct)
            }
        }
    }

    private fun showProductDetail(product: ProductUiModel) {
        val intent = getIntent(this, product, null)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun showCartScreen() = startActivity(CartActivity.getIntent(this))

    override fun showSelectCountScreen(product: ProductUiModel) {
        val binding = DialogSelectCountBinding.inflate(LayoutInflater.from(this))
        binding.presenter = presenter
        val dialog: AlertDialog = createSelectCountDialog(binding)
        dialog.show()
    }

    private fun createSelectCountDialog(binding: DialogSelectCountBinding) =
        AlertDialog.Builder(this).apply {
            setView(binding.root)
            binding.countView.count = presenter.count
            binding.countView.plusClickListener = {
                presenter.increaseCount()
            }
            binding.countView.minusClickListener = {
                presenter.decreaseCount()
            }
        }.create()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_cancel_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cancel_action -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val PRODUCT_KEY = "PRODUCT_KEY"
        private const val RECENT_PRODUCT_KEY = "RECENT_PRODUCT_KEY"

        fun getIntent(
            context: Context,
            product: ProductUiModel,
            recentProduct: ProductUiModel? = null
        ): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PRODUCT_KEY, product)
            recentProduct?.let { intent.putExtra(RECENT_PRODUCT_KEY, recentProduct) }
            return intent
        }
    }
}
