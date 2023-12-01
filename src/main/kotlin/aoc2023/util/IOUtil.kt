package aoc2023.util

import java.io.InputStream

val InputStream.asString: String
    get () {
        val bufferSize = 1024
        val buffer = CharArray(bufferSize)
        val out = StringBuilder()
        val istr = java.io.InputStreamReader (this, "UTF-8")
        while (true) {
            val rsz = istr.read(buffer, 0, buffer.size)
            if (rsz < 0)
                break
            out.appendRange(buffer, 0, rsz)
        }
        return out.toString ()
    }

object IOUtil {

}

// EOF