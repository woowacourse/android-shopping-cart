package woowacourse.shopping.view

import androidx.fragment.app.Fragment

interface MainFragmentListener {
    fun changeFragment(nextFragment: Fragment)

    fun popFragment()

    fun observeProductList(products: (Map<Long,Int>)-> Unit)
}
