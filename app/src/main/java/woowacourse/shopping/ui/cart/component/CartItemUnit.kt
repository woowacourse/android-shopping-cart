package woowacourse.shopping.ui.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.cart.CartItemUiModel
import woowacourse.shopping.ui.common.component.cartcontrol.QuantityStepper
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun CartItemUnit(
    item: CartItemUiModel,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(1.dp, ShoppingColors.Gray2, RoundedCornerShape(4.dp))
                .padding(18.dp),
    ) {
        NameAndCloseIcon(item = item, onClick = onDeleteClick)

        Spacer(Modifier.size(20.dp))

        ImageAndPrice(
            item = item,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
        )
    }
}

@Composable
private fun NameAndCloseIcon(
    item: CartItemUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.name,
            color = ShoppingColors.Gray4,
            style = ShoppingTypography.productName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.content_description_close),
            modifier = Modifier.clickable(onClick = onClick),
            tint = ShoppingColors.Gray2,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ImageAndPrice(
    item: CartItemUiModel,
    modifier: Modifier = Modifier,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    val formatted = String.format("%,d", item.price * item.quantity)

    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = stringResource(R.string.content_description_image),
            modifier =
                Modifier
                    .width(136.dp)
                    .height(72.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier =
                Modifier
                    .height(72.dp)
                    .padding(end = 6.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
        ) {
            QuantityStepper(
                quantity = item.quantity,
                onIncreaseQuantity = onIncreaseQuantity,
                onDecreaseQuantity = onDecreaseQuantity,
            )
            Text(
                text = stringResource(R.string.price_format, formatted),
                color = ShoppingColors.Gray4,
                style = ShoppingTypography.productPrice,
            )
        }
    }
}

@Preview(showBackground = true, name = "카트 아이템 유닛")
@Composable
private fun CartItemUnitPreview() {
    CartItemUnit(
        item =
            CartItemUiModel(
                productId = InMemoryProductRepository.APPLE.id,
                name = InMemoryProductRepository.APPLE.name,
                imageUrl = InMemoryProductRepository.APPLE.imageUrl,
                price = InMemoryProductRepository.APPLE.price.value,
                quantity = 2,
            ),
        onDeleteClick = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}

@Preview(showBackground = true, name = "이름과 닫기아이콘")
@Composable
private fun NameAndCloseIconPreview() {
    NameAndCloseIcon(
        item =
            CartItemUiModel(
                productId = InMemoryProductRepository.APPLE.id,
                name = InMemoryProductRepository.APPLE.name,
                imageUrl = InMemoryProductRepository.APPLE.imageUrl,
                price = InMemoryProductRepository.APPLE.price.value,
                quantity = 2,
            ),
        onClick = {},
    )
}

@Preview(showBackground = true, name = "사진과 금액")
@Composable
private fun ImageAndPricePreview() {
    ImageAndPrice(
        item =
            CartItemUiModel(
                productId = InMemoryProductRepository.APPLE.id,
                name = InMemoryProductRepository.APPLE.name,
                imageUrl = InMemoryProductRepository.APPLE.imageUrl,
                price = InMemoryProductRepository.APPLE.price.value,
                quantity = 2,
            ),
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
