package woowacourse.shopping.feature.goodsdetails

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.carts.repository.CartRepositoryImpl
import woowacourse.shopping.data.goods.repository.GoodsLocalDataSourceImpl
import woowacourse.shopping.data.goods.repository.GoodsRemoteDataSourceImpl
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.data.util.MockInterceptor.Companion.mockOkHttpClient
import woowacourse.shopping.databinding.ActivityGoodsDetailsBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.feature.QuantityChangeListener
import woowacourse.shopping.util.toUi

class GoodsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsDetailsBinding

    private val goodsUiModel by lazy {
        IntentCompat.getParcelableExtra(intent, GOODS_KEY, GoodsUiModel::class.java)
            ?: throw IllegalArgumentException("GoodsUiModel is required")
    }
    private val viewModel: GoodsDetailsViewModel by viewModels {
        GoodsDetailsViewModelFactory(
            goodsUiModel,
            CartRepositoryImpl(ShoppingDatabase.getDatabase(this)),
            GoodsRepositoryImpl(
                GoodsRemoteDataSourceImpl(okHttpClient = mockOkHttpClient),
                GoodsLocalDataSourceImpl(ShoppingDatabase.getDatabase(this)),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        initializeViewModelData()
        setupEventListeners()
        observeViewModelEvents()
    }

    private fun initializeBinding() {
        binding = ActivityGoodsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initializeViewModelData() {
        val source = intent.getStringExtra(EXTRA_SOURCE)
        if (source != SOURCE_RECENTLY_VIEWED) {
            viewModel.initMostRecentlyViewedGoods()
        }
    }

    private fun setupEventListeners() {
        binding.quantityChangeListener = createQuantityChangeListener()
    }

    private fun createQuantityChangeListener(): QuantityChangeListener =
        object : QuantityChangeListener {
            override fun onIncrease(cartItem: CartItem) {
                viewModel.increaseSelectorQuantity()
            }

            override fun onDecrease(cartItem: CartItem) {
                viewModel.decreaseSelectorQuantity()
            }
        }

    private fun observeViewModelEvents() {
        observeAlertEvents()
        observeMostRecentlyGoodsEvents()
    }

    private fun observeAlertEvents() {
        viewModel.alertEvent.observe(this) { goodsDetailsAlertMessage ->
            showMessage(
                getString(
                    goodsDetailsAlertMessage.resourceId,
                    goodsDetailsAlertMessage.quantity,
                ),
            )
        }
    }

    private fun observeMostRecentlyGoodsEvents() {
        viewModel.clickMostRecentlyGoodsEvent.observe(this) { mostRecentGoods ->
            val intent = fromDetails(this, mostRecentGoods.toUi())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_close, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
        Toast
            .makeText(
                this,
                message,
                Toast.LENGTH_SHORT,
            ).show()
    }

    companion object {
        private const val GOODS_KEY = "GOODS"
        private const val EXTRA_SOURCE = "extra_source"
        private const val SOURCE_RECENTLY_VIEWED = "recently_viewed"
        private const val SOURCE_GOODS_LIST = "goods_list"

        fun fromGoods(
            context: Context,
            goods: GoodsUiModel,
        ): Intent =
            createBaseIntent(context, goods).apply {
                putExtra(EXTRA_SOURCE, SOURCE_GOODS_LIST)
            }

        fun fromDetails(
            context: Context,
            goods: GoodsUiModel,
        ): Intent =
            createBaseIntent(context, goods).apply {
                flags = FLAG_ACTIVITY_CLEAR_TOP
                putExtra(EXTRA_SOURCE, SOURCE_RECENTLY_VIEWED)
            }

        private fun createBaseIntent(
            context: Context,
            goods: GoodsUiModel,
        ): Intent =
            Intent(context, GoodsDetailsActivity::class.java).apply {
                putExtra(GOODS_KEY, goods)
            }
    }
}
