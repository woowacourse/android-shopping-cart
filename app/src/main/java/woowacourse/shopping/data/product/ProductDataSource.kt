package woowacourse.shopping.data.product

interface ProductDataSource {
    fun getProductEntity(id: Long): ProductEntity?
    fun getProductEntities(unit: Int, lastIndex: Int): List<ProductEntity>
    fun addProductEntity(
        name: String,
        price: Int,
        itemImage: String,
    ): Long
}
