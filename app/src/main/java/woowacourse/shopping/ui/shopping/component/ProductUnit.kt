package woowacourse.shopping.ui.shopping.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@SuppressLint("DefaultLocale")
@Composable
fun ProductUnit(
    product: Product,
    modifier: Modifier = Modifier
) {
    val price = product.price.value
    val formatted = String.format("%,d", price)
    Column(
        modifier = modifier
            .width(154.dp)
            .height(206.dp)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "이미지",
            modifier = Modifier.size(154.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(6.dp))
        Text(
            text = product.name,
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp)
        )
        Text(
            text = "$formatted 원",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductUnitPreview() {
    ProductUnit(InMemoryProductRepository.APPLE)
}
