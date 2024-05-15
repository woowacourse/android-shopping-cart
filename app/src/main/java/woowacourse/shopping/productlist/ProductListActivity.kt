package woowacourse.shopping.productlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity(), ProductListContract.ViewAction {
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcvProductList.adapter =
            ProductListAdapter(this).apply {
                this.submitList(DummyShoppingRepository.products().map { it.toProductList() })
            }

        supportActionBar?.title = "Shopping"
    }

    override fun onProductClicked(id: Long) {
        startActivity(ProductDetailActivity.newInstance(this, id))
    }
}
