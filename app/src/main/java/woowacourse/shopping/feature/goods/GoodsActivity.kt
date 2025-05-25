package woowacourse.shopping.feature.goods

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.carts.repository.CartRepositoryImpl
import woowacourse.shopping.data.goods.repository.GoodsLocalDataSourceImpl
import woowacourse.shopping.data.goods.repository.GoodsRemoteDataSourceImpl
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.databinding.MenuCartNavbarBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.QuantityChangeListener
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goods.adapter.horizontal.HorizontalSectionAdapter
import woowacourse.shopping.feature.goods.adapter.horizontal.RecentlyViewedGoodsAdapter
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.vertical.MoreButtonAdapter
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity.Companion.EXTRA_SOURCE
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity.Companion.SOURCE_GOODS_LIST
import woowacourse.shopping.util.toUi

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    private lateinit var navbarBinding: MenuCartNavbarBinding
    private val viewModel: GoodsViewModel by viewModels {
        GoodsViewModelFactory(
            CartRepositoryImpl(ShoppingDatabase.getDatabase(this)),
            GoodsRepositoryImpl(
                GoodsRemoteDataSourceImpl(),
                GoodsLocalDataSourceImpl(ShoppingDatabase.getDatabase(this)),
            ),
        )
    }

    private val recentlyViewedGoodsAdapter by lazy {
        RecentlyViewedGoodsAdapter(this) { goods -> navigateGoodsDetails(goods) }
    }
    private val horizontalSelectionAdapter by lazy {
        HorizontalSectionAdapter(this, viewModel, recentlyViewedGoodsAdapter)
    }
    private val goodsAdapter by lazy {
        GoodsAdapter(
            goodsClickListener = { goods -> navigateGoodsDetails(goods) },
            quantityChangeListener =
                object : QuantityChangeListener {
                    override fun onIncrease(cartItem: CartItem) {
                        viewModel.addCartItemOrIncreaseQuantity(cartItem.copy(quantity = 1))
                    }

                    override fun onDecrease(cartItem: CartItem) {
                        viewModel.removeCartItemOrDecreaseQuantity(cartItem.copy(quantity = 1))
                    }
                },
        )
    }
    private val moreButtonAdapter by lazy {
        MoreButtonAdapter {
            viewModel.addPage()
            viewModel.updateCartQuantity()
        }
    }
    private val concatAdapter by lazy {
        ConcatAdapter(
            horizontalSelectionAdapter,
            goodsAdapter,
            moreButtonAdapter,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.rvGoodsItems.adapter = concatAdapter
        binding.viewModel = viewModel

        binding.rvGoodsItems.layoutManager = getLayoutManager()

        viewModel.navigateToCart.observe(this) {
            val intent = CartActivity.newIntent(this)
            startActivity(intent)
        }

        viewModel.goodsWithCartQuantity.observe(this) {
            viewModel.updateCartQuantity()
        }
        binding.rvGoodsItems.addItemDecoration(
            GoodsGridItemDecoration(concatAdapter, GRID_GOODS_ITEM_HORIZONTAL_PADDING),
        )

        viewModel.recentlyViewedGoods.observe(this) { goods ->
            recentlyViewedGoodsAdapter.setItems(goods)
        }
    }

    private fun getLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val (adapter, _) = concatAdapter.getWrappedAdapterAndPosition(position)
                    return when (adapter) {
                        is GoodsAdapter -> 1
                        else -> 2
                    }
                }
            }
        return layoutManager
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCartQuantity()
        viewModel.updateRecentlyViewedGoods()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_cart, menu)
        val menuItem = menu?.findItem(R.id.nav_cart)
        navbarBinding = MenuCartNavbarBinding.inflate(layoutInflater)
        navbarBinding.lifecycleOwner = this
        navbarBinding.viewModel = viewModel
        menuItem?.actionView = navbarBinding.root

        return super.onCreateOptionsMenu(menu)
    }

    private fun navigateGoodsDetails(goods: Goods) {
        val intent = GoodsDetailsActivity.newIntent(this, goods.toUi())
        intent.putExtra(EXTRA_SOURCE, SOURCE_GOODS_LIST)
        startActivity(intent)
    }

    companion object {
        private const val GRID_GOODS_ITEM_HORIZONTAL_PADDING = 14
    }
}
