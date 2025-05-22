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
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.databinding.MenuCartNavbarBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.QuantityChangeListener
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.MoreButtonAdapter
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity
import woowacourse.shopping.util.toUi

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    private lateinit var navbarBinding: MenuCartNavbarBinding
    private val viewModel: GoodsViewModel by viewModels {
        GoodsViewModelFactory(CartRepositoryImpl(ShoppingDatabase.getDatabase(this)))
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
    private val concatAdapter by lazy { ConcatAdapter(goodsAdapter, moreButtonAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.rvCartItems.adapter = concatAdapter
        binding.viewModel = viewModel

        binding.rvCartItems.layoutManager = getLayoutManager()

        viewModel.navigateToCart.observe(this) {
            val intent = CartActivity.newIntent(this)
            startActivity(intent)
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
        startActivity(intent)
    }
}
