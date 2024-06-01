package woowacourse.shopping.data.db.mapper

import woowacourse.shopping.data.db.model.ProductBrowsingHistoryEntity
import woowacourse.shopping.domain.model.ProductBrowsingHistory

fun ProductBrowsingHistoryEntity.toHistory(): ProductBrowsingHistory = ProductBrowsingHistory(product.toProduct(), timestamp)

fun ProductBrowsingHistory.toEntity(): ProductBrowsingHistoryEntity =
    ProductBrowsingHistoryEntity(product = product.toEntity(), timestamp = timestamp)

fun List<ProductBrowsingHistoryEntity>.toHistory(): List<ProductBrowsingHistory> =
    this.map {
        it.toHistory()
    }

fun List<ProductBrowsingHistory>.toEntity(): List<ProductBrowsingHistoryEntity> =
    this.map {
        it.toEntity()
    }
