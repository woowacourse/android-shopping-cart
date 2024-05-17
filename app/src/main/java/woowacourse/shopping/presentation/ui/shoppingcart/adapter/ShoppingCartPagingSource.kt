// package woowacourse.shopping.presentation.ui.shoppingcart.adapter
//
// import woowacourse.shopping.domain.model.Product
// import woowacourse.shopping.domain.repository.ProductRepository
//
// class ShoppingCartPagingSource(
//    private val repository: ProductRepository,
// ) {
//    private val data: MutableMap<Int, List<Product>> = mutableMapOf()
//
//    fun load(
//        page: Int,
//        pageSize: Int,
//    ): List<Product> {
//        if (data.containsKey(page)) {
//          return  data[page]!!
//        } else {
//            emptyList<Product>()
//        }
//
//        val result = repository.getPagingProduct(page = page, pageSize = pageSize)
//
//        return result.fold(
//            onSuccess = {
//                        it
//
//            },
//            onFailure = {
//
//            }
//        )
// //
// //                val fromIndex = page * pageSize
// //                val toIndex = min(fromIndex + pageSize, productList.size)
// //                val last = toIndex == productList.size
// //                PagingProduct(
// //                    currentPage = page,
// //                    productList = productList.subList(fromIndex, toIndex),
// //                    last = last,
// //                )
//    }
// }
