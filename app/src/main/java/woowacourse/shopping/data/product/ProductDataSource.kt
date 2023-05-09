package woowacourse.shopping.data.product

interface ProductDataSource {
    fun getProductEntity(id: Long): ProductEntity?
    fun getProductEntities(unit: Int, lastIndex: Int): List<ProductEntity>
    fun addProductEntity(
        name: String,
        itemImage: String,
        price: Int,
    ): Long
}
