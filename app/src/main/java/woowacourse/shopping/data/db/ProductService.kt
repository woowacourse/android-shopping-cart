package woowacourse.shopping.data.db

import com.shopping.domain.Product

interface ProductService {
    fun request(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )
}