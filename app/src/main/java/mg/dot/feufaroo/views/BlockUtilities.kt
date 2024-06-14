package mg.dot.feufaroo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mg.dot.feufaroo.R
import mg.dot.feufaroo.ui.theme.FeufarooTheme

// parameters for that file
val separators = listOf(":", "|", "/")
const val endBlock = "===="
// TODO: work on logic behind lyrics
const val lyrics = "[]"

var beginLine = 0
var endLine = 6

fun createBlocks(textLines: List<String>, separators: List<String>, endBlock: String, lyrics: String) : MutableList<Block> {
    val blocks : MutableList<Block> = mutableListOf()

    // TODO: define a logic for the number of objects
    for (i in 1..48){
        val block = Block()

        for (line in textLines.slice(beginLine..endLine)){
            when (line) {
                in separators -> block.separator = line
                lyrics -> continue
                endBlock -> break
                else -> block.choir.add(line)    // test validity of the solfa
            }
        }
        blocks.add(block)
        endLine++
        beginLine = endLine
        endLine += 6

    }
    return blocks
}


//var block = Block(separator = ":", choir = mutableListOf("s1","m1","d","d1"))
//var block2 = Block(separator = ":", choir = mutableListOf("f","l1","l1","r1"))

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

@Composable
fun DisplayAllCard(){

    val context = LocalContext.current
    fun getRawTextFile( resourceId: Int): String {
        val inputStream = context.resources.openRawResource(resourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        return String(buffer)
    }
    val fileContent = getRawTextFile(R.raw.projecttemplaterefactor)
    val textLines = fileContent.split("\r\n")

    val blocks = createBlocks(textLines, separators, endBlock, lyrics)

    LazyRow(modifier = Modifier.padding(5.dp)) {
        items(blocks) { block -> BlockCard(block) }
    }
}

@Preview(showBackground = true)
@Composable
fun BlockCardPreview () {
    FeufarooTheme {
        Row(
            modifier = Modifier.padding(1.dp)
        ) {
//            BlockCard(block = block)
//            BlockCard(block2)


//            LazyRow(modifier = Modifier.padding(5.dp)) {
//                items(blocks) { block -> BlockCard(block) }
//            }
        }
    }
}