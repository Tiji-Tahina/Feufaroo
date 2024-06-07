package mg.dot.feufaroo.views

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import com.example.feufaroo.R

class BlockScrollingFragment : androidx.fragment.app.Fragment() {
    private fun Resources.getRawTextFile(@RawRes resourceId: Int): String {
        val inputStream = openRawResource(resourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        return String(buffer)
    }
    val fileContent = resources.getRawTextFile(R.raw.projecttemplaterefactor)
    val textLinesFromText = fileContent.split("/n")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_block_scrolling, container, false)
    }
}