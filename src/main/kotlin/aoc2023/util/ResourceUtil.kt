package aoc2023.util

import java.io.*
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8

/**
 * Utility functions for retrieving things from the resource directory.
 */

object ResourceUtil {
    fun getPath (path: String): URL {
        val cls = Thread.currentThread().contextClassLoader
        return cls.getResource (path)
    }

    fun getAsStream (path: String): InputStream {
        val cls = Thread.currentThread().contextClassLoader
        val inputStream = cls.getResourceAsStream(path) ?: throw IOException ("Resource not found: $path")
        return inputStream
    }

    fun getAsReader (path: String): Reader {
        return Thread.currentThread ().contextClassLoader.getResourceAsStream(path)?.reader (UTF_8) ?: throw IOException ("Resource not found: $path")
    }

    fun getAsString (path: String): String {
        val cls = Thread.currentThread().contextClassLoader
        val ins = cls.getResourceAsStream(path) ?: throw IOException ("Resource not found: $path")
        return ins.asString
    }
}

// EOF