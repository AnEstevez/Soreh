package com.andresestevez.soreh.ui.screens.common

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.andresestevez.soreh.R
import com.andresestevez.soreh.data.models.Character


fun shareCharacter(context: Context, character: Character) {

    val idUrl = character.thumb.substringAfterLast(delimiter = "/").substringBefore(".")

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TITLE,
            character.name + " " + context.getString(R.string.complete_info)
        )
        putExtra(Intent.EXTRA_SUBJECT, character.name)
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.base_url_superherodb) + idUrl)
        type = ClipDescription.MIMETYPE_TEXT_PLAIN
    }

    val openIntent = Intent().apply {
        action = Intent.ACTION_VIEW
        setDataAndType(
            Uri.parse(context.getString(R.string.base_url_superherodb) + idUrl),
            ClipDescription.MIMETYPE_TEXT_PLAIN
        )
    }

    Intent.createChooser(sendIntent, null)
        .putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(openIntent))
        .also { context.startActivity(it) }
}