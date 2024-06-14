package mg.dot.feufaroo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mg.dot.feufaroo.R
import mg.dot.feufaroo.ui.theme.FeufarooTheme

class BlockScrollingFragment : androidx.fragment.app.Fragment() {

    private fun getRawTextFile( resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        return String(buffer)
    }
    private val fileContent = getRawTextFile(R.raw.projecttemplaterefactor)
    val textLinesFromText = fileContent.split("/n")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                Text(text = "Hello BlockScrollingFragment here !")
            }
        }
    }
}

@Composable
fun BlockCard (block: Block) {
    Row (
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SeparatorComponent(block.separator)
        ChoirComponent(block.choir)
    }
}

@Composable
fun SeparatorComponent(separator : String){
    Column(
        modifier = Modifier.padding(4.dp)
    ){
        Text(separator, onTextLayout = {})
        Text(separator, onTextLayout = {})
        Text(separator, onTextLayout = {})
        Text(separator, onTextLayout = {})
    }
}

@Composable
fun ChoirComponent(choir : List<String>){
    Column (
        modifier = Modifier.padding(4.dp)
    ){
        Text(text = choir[0], onTextLayout = {})
        Text(text = choir[1], onTextLayout = {})
        Text(text = choir[2], onTextLayout = {})
        Text(text = choir[3], onTextLayout = {})
    }
}

@Preview(showBackground = true)
@Composable
fun BlockCardPreview () {
    FeufarooTheme {
        Row(
            modifier = Modifier.padding(1.dp)
        ) {
            BlockCard(block = block)
//            BlockCard(block2)


//            LazyRow(modifier = Modifier.padding(5.dp)) {
//                items(blocks) { block -> BlockCard(block) }
//            }
        }
    }
}