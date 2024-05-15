package woowacourse.shopping.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.util.dp

class ProductListFragment :
    BindingFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            val productAdapter = ProductAdapter(onClickItem = { navigateToDetailView(it.toLong()) })
            rvProductList.adapter = productAdapter
            rvProductList.addItemDecoration(ProductItemDecoration(12.dp))
            // TODO 삭제 ><
            productAdapter.updateProducts(
                listOf(
                    ProductUi(
                        "오둥이",
                        1000,
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
                    ),
                    ProductUi(
                        "오둥둥",
                        1000,
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba1849316fb33a4b4cf43b6605fc7a1e262f0845",
                    ),
                    ProductUi(
                        "오둥2",
                        1000,
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba184931a88f7b2cbb72be0bdfff91ad65b168ab",
                    ),
                    ProductUi(
                        "꼬상",
                        1000,
                        "https://w7.pngwing.com/pngs/921/264/png-transparent-chipmunk-chip-n-dale-sticker-the-walt-disney-company-goofy-others.png",
                    ),
                    ProductUi(
                        "꼬상꼬상",
                        1000,
                        "https://i.namu.wiki/i/YvceZuAFsjYzbrTKYS09muExzVUw0f5JFBTAOLeCJbyeKghRLpkDnc5_XmQ9KvOpyRqz3zSWVZq5DpeW0HToWQ.webp",
                    ),
                ),
            )
        }
    }

    // TODO ViewModel 로 로작 이동
    private fun navigateToDetailView(id: Long) {
        parentFragmentManager.commit {
            replace<ProductDetailFragment>(
                R.id.fragment_container_shopping,
                ProductDetailFragment.TAG,
            )
            addToBackStack(TAG)
        }
    }

    companion object {
        val TAG: String? = ProductListFragment::class.java.canonicalName
    }
}
