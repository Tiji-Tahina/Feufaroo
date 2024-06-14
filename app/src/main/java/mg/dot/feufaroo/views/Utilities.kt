package mg.dot.feufaroo.views

// TODO: use the context with permissions but not absolute path like that
//var fileName = "C:\\Users\\26134\\AndroidStudioProjects\\Feufaroo\\app\\src\\main\\java\\mg\\dot\\feufaroo\\assets\\projecttemplaterefactor.txt"
//val textLines = File(fileName).bufferedReader().readLines()

// Try the fragment context



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

//val blocks = createBlocks(textLines, separators, endBlock, lyrics)

//*
var block = Block(separator = ":", choir = mutableListOf("s1","m1","d","d1"))
var block2 = Block(separator = ":", choir = mutableListOf("f","l1","l1","r1"))
//*
