package mg.dot.feufaroo.model

class Block {
    var choir: MutableList<String> = mutableListOf()
    var noteWidth: Int = 0
    var width: String = ""

    var separator: String = ""
    var nbNote: Int = 0
    private var nbLyrics: Int = 0
    private var marker: Any? = null
    private var template: String = ""
    private var noteString: String = ""
    private var lyricsString: String = ""

    private lateinit var meta: MutableMap<String?, String?>
    private lateinit var notes: MutableList<MutableList<String>> // choirs?
    private lateinit var lyrics: MutableList<String>
    private lateinit var noteMark: MutableList<MutableList<MutableList<String>>>
    private lateinit var numBlock: Any

    companion object {
        var maxWidth: Any? = null
        var nbBlock: Int = 0
        val noteToKey: Map<String, Int> = mapOf(
            "d,," to 0,
            "di,," to 1, "D,," to 1,
            "r,," to 2,
            "ri,," to 3, "R,," to 3,
            "m,," to 4,
            "f,," to 5,
            "fi,," to 6, "F,," to 6,
            "s,," to 7,
            "si,," to 8, "S,," to 8,
            "l,," to 9,
            "ta,," to 10, "T,," to 10,
            "t,," to 11,
            "d," to 12,
            "di," to 13, "D," to 13,
            "r," to 14,
            "ri," to 15, "R," to 15,
            "m," to 16,
            "f," to 17,
            "fi," to 18, "F," to 18,
            "s," to 19,
            "si," to 20, "S," to 20,
            "l," to 21,
            "ta," to 22, "T," to 22,
            "t," to 23,
            "d" to 24,
            "di" to 25, "D" to 25,
            "r" to 26,
            "ri" to 27, "R" to 27,
            "m" to 28,
            "f" to 29,
            "fi" to 30, "F" to 30,
            "s" to 31,
            "si" to 32, "S" to 32,
            "l" to 33,
            "ta" to 34, "T" to 34,
            "t" to 35,
            "d'" to 36,
            "di'" to 37, "D'" to 37,
            "r'" to 38,
            "ri'" to 39, "R'" to 39,
            "m'" to 40,
            "f'" to 41,
            "fi'" to 42, "F'" to 42,
            "s'" to 43,
            "si'" to 44, "S'" to 44,
            "l'" to 45,
            "ta'" to 46, "T'" to 46,
            "t'" to 47,
            "d''" to 48,
            "di''" to 49, "D''" to 49,
            "r''" to 50,
            "ri''" to 51, "R''" to 51,
            "m''" to 52,
            "f''" to 53,
            "fi''" to 54, "F''" to 54,
            "s''" to 55,
            "si''" to 56, "S''" to 56,
            "l''" to 57,
            "ta''" to 58, "T''" to 58,
            "t''" to 59
        )
        var keyToNote: Map<Int, String> = noteToKey.entries.associate { it.value to it.key }
    }

    constructor(templateNote: String, separator: String, marker: String, meta: MutableMap<String?, String?>){
        this.template = templateNote.replace("{", "").replace("}", "")
        this.separator = separator
        this.nbNote = templateNote.replace(Regex("/[^DRMFSLT]/"), "").length
        val templateSyllable = templateNote.replace(Regex("/\\([^\\)]*\\)/"), "D")
        this.nbLyrics = templateSyllable.replace(Regex("/[^DRMFSLT]/"), "").length
        this.marker = marker
        this.meta = meta
        this.numBlock = nbBlock
        nbBlock++
    }
    fun getNum(): Any {
        return this.numBlock
    }
    fun getLyricsHeight(): Int {
        return this.lyrics.size
    }
    fun getNoteHeight(): Int {
        return this.notes.size
    }
    fun getNbNote() : Any{
        return this.nbNote
    }
    fun getNbLyrics() : Any {
        return this.nbLyrics
    }
    fun setNote(sub : MutableList<MutableList<String>>) {
        if (sub.isEmpty()) { return } // null?
        this.notes = sub
        this.noteAsMultiString()
    }
    private fun setMark(noteMark : MutableList<MutableList<MutableList<String>>>) {
        if (noteMark.isEmpty()){ return }
        this.noteMark = noteMark
    }
    private fun getMark(i : Int) : List<String> {
        if (i !in this.noteMark.indices) { return mutableListOf() }
        return this.noteMark[i].flatten()
    }
    fun setLyrics(sub : MutableList<String>) {
        if (sub.isEmpty()) {
            return
        }
        this.lyrics = sub
        this.lyricsAsMultiString()
    }
    private fun unMark(i : Int, mark : String) {
        this.noteMark[i].withIndex().forEach{ (index, arrayMark) ->
            arrayMark.withIndex().forEach(){(numMark, valMark) ->
                if (mark == valMark) {
                    this.noteMark[i][index].removeAt(numMark)
                }
            }
        }
    }
    private fun noteAsMultiString(){
        val format = this.template.replace(Regex("/[DRMFSLT]/"), "%s")
        var returns = ""
        val underlined : MutableList<MutableList<MutableList<String>>> = mutableListOf(mutableListOf(mutableListOf("", "")))

        this.notes.withIndex().forEach() { (i, note) ->

            for (k in 0 until 100) {
                note.add("t")
            }

            note.withIndex().forEach() { (k: Int, v: String) ->
                val keyIndexOrigin = noteToKey[v] // "t" to 35 --middle?
                if (keyIndexOrigin != null) {
                    note[k] = keyToNote[keyIndexOrigin - (meta["transposeValue"]?.toInt() ?: 0)].toString()
                }
            }

            var formatted = format.format(note)
            val markVoice = getMark(i)

            if ("(" in markVoice) {
                if (formatted.take(1) != "(") {
                    formatted = "($formatted"
                }
                this.unMark(i, "(")
            }
            if ("[" in markVoice) {
                if (formatted.take(1) != "[") {
                    formatted = "[$formatted"
                }
                this.unMark(i, "[")
            }
            if (")" in markVoice) {
                if (!formatted.endsWith(")")) {
                    formatted = "$formatted)"
                }
                this.unMark(i, ")")
            }
            if ("]" in markVoice) {
                if (!formatted.endsWith("]")) {
                    formatted = "$formatted]"
                }
                this.unMark(i, "]")
            }
            formatted = formatted.replace("-.-", "-")
            formatted = formatted.replace("0.0", "")
            formatted = formatted.replace(Regex("/D|R|F|S|T/")) { matchResult ->
                when (matchResult.value) {
                    "D" -> "di"
                    "R" -> "ri"
                    "F" -> "fi"
                    "S" -> "si"
                    "T" -> "ta"
                    else -> ""
                }
            }
            formatted = formatted.replace(".-)", ")")
            formatted = formatted.replace(".-]", "]")
            formatted = formatted.replace("/\\((.i*\\'*,*)\\)/", "\\1")
            formatted = formatted.replace("/\\[(.i*\\'*,*)\\]/", "\\1")
            formatted = formatted.replace("/\\.,-\$/", "")
            formatted = formatted.replace(",,", "₂")
            formatted = formatted.replace("/(?<=[drmfsltia]),/", "₁")
            formatted = formatted.replace("/^0/", "")
            formatted = formatted.replace("/^\\.,0\$/", "")
            formatted = formatted.replace("/0\$/", "")
            formatted = formatted.replace("/^\\-\\.,/", ".,")
            if ("0" in formatted) {
                println(formatted)
            }
            var regex = """^\((.+)\)$""".toRegex()
            if (regex.matches(formatted)) {
                val matchResult = regex.find(formatted)
                val capturedGroup = matchResult?.groupValues?.get(1)
                formatted = capturedGroup ?: formatted
                underlined[i] = mutableListOf(mutableListOf("(",")"))
            }
            regex = """/^\\[(.*)\\]\$/""".toRegex()
            if (regex.matches(formatted)) {
                val matchResult = regex.find(formatted)
                val capturedGroup = matchResult?.groupValues?.get(1)
                formatted = capturedGroup ?: formatted
                underlined[i] = mutableListOf(mutableListOf("[","]"))
            }
            regex = """/[\(\)\[\]]/""".toRegex()
            if (regex.matches(formatted)) {
                val matchResult = regex.find(formatted)
                val capturedGroup = matchResult?.groupValues?.get(1)
                formatted = capturedGroup ?: formatted
                if (matchResult != null) {
                    underlined[i] = mutableListOf(mutableListOf(matchResult.value))
                }

            }
            returns += formatted + "\n"
        }
        this.noteString = returns.replaceFirst("^\\s+","").replaceFirst("\\s+$","") // trimmed
        this.setMark(underlined)
    }

    private fun lyricsAsMultiString(){
        this.lyricsString = this.lyrics.joinToString("\n")
        // this.calcMinWidth()
    }
}