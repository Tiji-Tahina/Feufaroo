package mg.dot.feufaroo.model

import java.util.Locale

class Solfa {

    private lateinit var separators: List<String>
    private lateinit var fileData : Map<String, MutableList<String>>
    private lateinit var meta : MutableMap<String?, String?>
    private lateinit var note : MutableList<String>
    private lateinit var template : MutableList<String>
    private lateinit var lyrics : MutableList<String>

    private var iBlock : Int = 0
    private var iNote : Int = 0
    private var iLyrics : Int = 0

    private lateinit var marker : MutableList<String>

    private var lyricsLine : Int = 0
    private var hairpin : Any? = null

    // x, y, pdf : Any
    private lateinit var options : MutableMap<String, Any> // Boolean, Int, ?

    // path : String = "assets/samples/solfa-60.txt",
    constructor(options : MutableMap<String, Any>) {
        // redoing the
        this.options = options

        // val sourceFile = File(path)
        // val sourceAsArray : List<String> = sourceFile.readLines()
        // arrayWalk(sourceAsArray, MutableList(this, "parseAllLines"));

//        this.loadMeta()
//        this.loadSeparators()
//        this.loadNotes()
//        this.loadAllLyrics()
//        this.loadNoteTemplate()
//        this.setupBlocks()
    }
    fun parseAllLines(line : String){
        val key : String
        val index : Int
        val value : String

        if(":" == line.substring(2, 1)){
            key = line.substring(0, 1)
            index = line.substring(1, 1).toIntOrNull() ?: 0
            value = line.substring(2)
        } else {
            key = line.substring(0, 3)
            index = line.substring(1, 2).toIntOrNull() ?: 0
            value = line.substring(4)
        }
        if("" != key.trim()){
            this.fileData[key]?.set(index, value.trimEnd())
        }
    }
    // fun debug() { print_r(this) }
    fun loadMeta(){
//        val txtMetaLine = this.fileData["M"]?.get(0)
//        val regex = "/\\|(?=[a-z]:)/".toRegex()
        // val metaItemArray = txtMetaLine?.split(regex)
//        arrayWalk(metaItemArray, MutableList(this, "getMeta"))
    }

    fun getMeta(metaItem : String) {
        /* $_a_keyAbbrev = array(
              'a' => 'author',
              'c' => 'tonality',
              'h' => 'composer',
              'i' => 'interligne',
              'l' => 'lyrics font size',
              'm' => 'rythm',
              'n' => 'note font size',
              'r' => 'speed',
              't' => 'title',
            );*/
        val key = metaItem.substring(0, 1)
        if ("" != key.trim() && "M" != key){
            this.meta[key] = metaItem.substring(2) // Integer
            if("c" == key){
                this.meta["C"] = this.meta[key]?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                val keyOrigin = this.tonalityToNumber(this.meta[key].toString()) // meta gives a Int? type
                var keyDest = keyOrigin
                var keyAsIf = keyOrigin

                val transposeTo = this.getOpt("transposeTo")//Int
                val transposeAsIf = this.getOpt("transposeAsIf")//String  // from getOpt
                if (transposeTo != false) {
                    keyDest = this.tonalityToNumber("transposeTo")
                }
                if (transposeAsIf != false) {
                    if (transposeAsIf is Int){
                        keyAsIf = transposeAsIf + keyOrigin
                    } else {
                        keyAsIf = this.tonalityToNumber(transposeAsIf.toString())
                    }
                }
                this.meta["transposeValue"] = (keyDest - keyAsIf).toString()
                if (keyAsIf != keyOrigin){
                    this.meta["C"] = this.numberToTonality(keyAsIf)
                }
                if (keyDest != keyOrigin){
                    this.meta["C"] += " (" + this.numberToTonality(keyDest) + ")"
                }
            }
        }
    }

    private fun getOpt(optionName : String): Any {
        return this.options[optionName] ?: false
    }
    private fun loadSeparators(){
        val separatorLine = this.fileData["S"]?.get(0) // let's try to make
        if (separatorLine != null) {
            this.separators = separatorLine.split("")
        }
    }

    private fun numberToTonality(number : Int): String {
        val numberToTonality = listOf("", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
        return numberToTonality[number]
    }

    private fun tonalityToNumber(tonality : String): Int {
        val tonalityToNumber = mapOf(
            "C" to 1,
            "C#" to 2,       "Db" to 2,
            "D" to 3,
            "D#" to 4,       "Eb" to 4,
            "E" to 5,
            "F" to 6,
            "F#" to 7,       "Gb" to 7,
            "G" to 8,
            "G#" to 9,       "Ab" to 9,
            "A" to 10,
            "A#" to 11,      "Bb" to 11,
            "B" to 12,
        )
        return tonalityToNumber.getOrDefault(tonality, -1)
    }

    private fun loadNoteTemplate() {
        val noteTemplate : String? = this.fileData["T"]?.get(0)
        var templateNotes = ""
        var noteMarker = ""
        /* Markers are :
        * $< or $> : marks starting of < or >
        * $= : marks end of < or >
        * $Q : point d'orgue
        */
        noteTemplate?.split("")?.forEach() { noteSymbol ->
            if (noteMarker != "") {
                if (noteSymbol != "}" && noteMarker.substring(0, 2) == "\${") {
                    noteMarker += noteSymbol
                    return@forEach
                }
                if (noteSymbol == "}" && noteMarker.substring(0, 2) == "\${") {
                    val regex = "/Do dia ([A-G]b?)/".toRegex()
                    if (regex.matches(noteMarker)) {
                        val matchResult = regex.find(noteMarker)
                        val newTonality = matchResult?.groupValues?.get(1)
                        val keyAsIf = newTonality?.let { this.tonalityToNumber(it) }
                        var keyOrigin = this.meta["c"]?.let { this.tonalityToNumber(it) }
                        val transposeTo = this.getOpt("transposeTo")
                        if (transposeTo != null) {
                            keyOrigin = this.tonalityToNumber(transposeTo.toString())
                            noteMarker += " (" + this.numberToTonality(keyOrigin) + ")"
                            this.meta["transposeValue"] = (keyOrigin - keyAsIf!!).toString()
                        }
                    }
                }

                noteMarker += noteSymbol

                if (noteSymbol != "{") {
                    this.marker.add(noteMarker)
                    noteMarker = ""
                }
                return@forEach
            }

            if (this.separators.contains(noteSymbol)){
                this.nextNote(templateNotes, noteSymbol)
                templateNotes = ""
                return@forEach
            }
            if ("$" == noteSymbol) {
                noteMarker = "$"
                return@forEach
            }
            templateNotes += noteSymbol
        }
        this.nextNote(templateNotes)
    }

    private fun nextNote(templateNote : String, separator : String = "") {
        val newBlock = Block(templateNote, separator, this.marker.toString(), this.meta)
        val (subNote, subMark) = this.getSubNotes(newBlock.nbNote)
        // subMark is an empty array
        // subNote is full of Arrays like subMark
        if (subMark.size != 0) {

        }
    }


//    private fun getSubNotes(nbNote : Int): MutableList<MutableList<MutableList<String>>> {
//        if (nbNote == 0){
//            val noteSize = this.note.size
//            val subNote = MutableList(noteSize) { mutableListOf("") }
//            val resultList = mutableListOf(subNote, mutableListOf()) // SubMark
//            return resultList
//        }
//        val subNote = mutableListOf("")
//        this.note.forEachIndexed() { kNote, vNote ->
//            subNote[kNote] = vNote.slice(this.iNote..nbNote)
//            val mark = mutableListOf("")
//            for(i in this.iNote .. this.iNote + nbNote ){
//                if (this.noteMarq)
//            }
//        }
//    }
private fun getSubNotes(nbNote: Int): MutableList<MutableList<MutableList<MutableList<String>>>> {
    val subNotes = mutableListOf<MutableList<MutableList<String>>>()
    val subMarks = mutableListOf<MutableList<MutableList<String>>>()

    // for each choir line (vertical column)
    for (i in 0 until nbNote) {
        val noteColumn = mutableListOf<MutableList<String>>()
        val markColumn = mutableListOf<MutableList<String>>()

        for (j in 0 until this.note.size) {
            val notesInLine = this.note[j].split(",").map { it.trim() }
            val note = notesInLine.getOrNull(this.iNote + i) ?: ""
            noteColumn.add(mutableListOf(note))

            val marksInLine = if (j < this.marker.size)
                this.marker[j].split(",").map { it.trim() }
            else
                listOf()

            val mark = marksInLine.getOrNull(this.iNote + i) ?: ""
            markColumn.add(mutableListOf(mark))
        }

        subNotes.add(noteColumn)
        subMarks.add(markColumn)
    }

    return mutableListOf(subNotes, subMarks)
}


}