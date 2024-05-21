package com.example.feufaroo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feufaroo.ui.theme.FeufarooTheme
import java.io.File


// read the file here
var fileName = "C:\\Users\\26134\\AndroidStudioProjects\\Feufaroo\\app\\src\\main\\java\\com\\example\\feufaroo\\assets\\ProjectTemplateSample.txt"
fun readFileLine(fileName: String): List<String> = File(fileName).bufferedReader().readLines()

val fileInline = readFileLine(fileName)

// TODO : make a loop from text to object
var block = Block(marking = "", separator = ":", choir = listOf("s1","m1","d","d1"), lyrics = "")
var block2 = Block(marking = "", separator = ":", choir = listOf("f","l1","l1","r1"), lyrics = "")



// make a composable for the block object
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
            // here also a loop for all the block objects ...
            BlockCard(block = block)
            BlockCard(block2)
        }
    }
}

// one LazyRow example
/*
@Composable
fun SampleDataInRows(){
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(fileInline) { line ->
            Text(line)
        }
    }
}
*/
