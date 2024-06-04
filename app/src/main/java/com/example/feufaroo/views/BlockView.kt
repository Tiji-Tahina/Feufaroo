package com.example.feufaroo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feufaroo.ui.theme.FeufarooTheme
import java.io.File


// TODO: use the context with permissions but not absolute path like that
var fileName = "C:\\Users\\26134\\AndroidStudioProjects\\Feufaroo\\app\\src\\main\\java\\com\\example\\feufaroo\\assets\\ProjectTemplateSample.txt"
fun readFileLine(fileName: String): List<String> = File(fileName).bufferedReader().readLines()

//Maybe clean the data
val textLines = readFileLine(fileName)
// parameters for that specific template sample
val separators = listOf(":", "|", "/")
const val endBlock = "===="
// TODO: work on logic behind lyrics
const val lyrics = "[]" // just skip it for now

var beginLine = 0
var endLine = 6

fun createBlocks(textLines: List<String>, separator: List<String>, endBlock: String, lyrics: String) : MutableList<Block> {
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

// creates a double in the test somehow
//val blocks = createBlocks(textLines, separators, endBlock, lyrics)

// Dummy blocks for tests
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
        Text(separator)
        Text(separator)
        Text(separator)
        Text(separator)
    }
}

@Composable
fun ChoirComponent(choir : List<String>){
    Column (
        modifier = Modifier.padding(4.dp)
    ){
        Text(text = choir[0])
        Text(text = choir[1])
        Text(text = choir[2])
        Text(text = choir[3])
    }
}

@Preview(showBackground = true)
@Composable
fun BlockCardPreview () {
    FeufarooTheme {
        Row(
            modifier = Modifier.padding(1.dp)
        ) {

//            LazyRow(
//                modifier = Modifier.padding(5.dp)
//            ) { items(blocks) { block -> BlockCard(block) } }
//
//            BlockCard(block = block)
//            BlockCard(block2)
        }
    }
}


