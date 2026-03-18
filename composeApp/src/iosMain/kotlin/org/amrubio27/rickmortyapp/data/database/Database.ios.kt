package org.amrubio27.rickmortyapp.data.database

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun getDatabase(): RickMortyDatabase {
    val dbFile = "${fileDirectory()}/$DATABASE_NAME"

    return Room.databaseBuilder<RickMortyDatabase>(name = dbFile)
        .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@OptIn(ExperimentalForeignApi::class)
fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        error = null,
        create = false,
        appropriateForURL = null
    )

    return requireNotNull(documentDirectory).path!!

}