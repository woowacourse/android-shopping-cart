package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    private lateinit var adpater: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setActionBar()
        setPresenter()
        presenter.fetchProducts()
    }

    private fun setBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ACTION_BAR_TITLE
    }

    private fun setPresenter() {
        presenter = CartPresenter(this, CartDbRepository(this), ProductMockRepository)
    }

    override fun showProducts(
        cartProducts: List<CartProductModel>,
        isExistUndo: Boolean,
        isExistNext: Boolean,
        count: String,
    ) {
        Log.d("test", "showProduct 진입")
        adpater = CartAdapter(
            cartProducts,
            object : CartAdapter.OnItemClick {
                override fun onRemoveClick(id: Int) {
                    presenter.removeProduct(id)
                }

                override fun onNextClick() {
                    presenter.fetchNextPage()
                }

                override fun onUndoClick() {
                    presenter.fetchUndoPage()
                }
            },
            isExistUndo,
            isExistNext,
            count,
        )
        binding.recyclerCart.adapter = adpater
    }

    override fun showOtherPage(size: Int) {
        adpater.updateCartItems(size)
    }

    override fun notifyRemoveItem(position: Int) {
        adpater.removeCartItems(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.handleNextStep(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun handleBackButtonClicked() {
        finish()
    }

    companion object {
        private const val ACTION_BAR_TITLE = "Cart"

        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
