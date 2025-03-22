package com.hskflashcard.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hskflashcard.data.source.local.LocalDataSource
import com.hskflashcard.data.source.local.room.HSKWord
import com.hskflashcard.data.source.local.room.HSKWordDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.BufferedReader

@HiltWorker
class HSKDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val hskWordDao: HSKWordDao,
    private val localDataSource: LocalDataSource
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val words = parseTSVFiles(applicationContext)
            hskWordDao.insertWords(words)

            localDataSource.saveHasBeenPopulated(true)
            return Result.success()
        } catch (e: Exception) {
            Log.e("HSKDataWorker", "Error parsing TSV files: ${e.message}")
            return Result.failure()
        }
    }

    private fun parseTSVFiles(context: Context): List<HSKWord> {
        val words = mutableListOf<HSKWord>()
        val files = arrayOf(
            "hsk-1-word-list",
            "hsk-2-word-list",
            "hsk-3-word-list",
            "hsk-4-word-list",
            "hsk-5-word-list",
            "hsk-6-word-list"
        )

        files.forEach { file ->
            val inputStream = context.assets.open("$file.tsv")
            val reader = BufferedReader(inputStream.reader())

            reader.forEachLine { line ->
                val tokens = line.split("\t")
                words.add(
                    HSKWord(
                        hskLevel = file.substringBefore("-word-list"),
                        simplified = tokens[1]
                            .replace("\uFF08", "(")
                            .replace("\uFF09", ")"),
                        pinyin = tokens[2],
                        translation = tokens[3]
                    )
                )
            }
            reader.close()
        }

        return words
    }

}