package ya.weber.lifework.presentation.ui.bottom

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppBottom(
    text: String,
    click: () -> Unit
) {
    Button(onClick = {
        click.invoke()
    }) {
        Text(text = text)
    }
}