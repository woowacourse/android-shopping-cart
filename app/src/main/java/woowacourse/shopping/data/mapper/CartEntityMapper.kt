package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.DataCartEntity
import woowacourse.shopping.domain.model.DomainCartEntity

fun DataCartEntity.toDomain(): DomainCartEntity = DomainCartEntity(
    id, productId, count, checked == 1
)

fun DomainCartEntity.toData(): DataCartEntity = DataCartEntity(
    id, productId, count, if (checked) 1 else 0
)
