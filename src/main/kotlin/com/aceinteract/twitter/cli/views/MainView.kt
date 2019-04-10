package com.aceinteract.twitter.cli.views


import com.aceinteract.twitter.cli.TwitterClient
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainView : View() {

    private val twitterClient = TwitterClient()

    private var file: File? = null

    private val status = SimpleStringProperty()

    private val mediaPath = SimpleStringProperty("No media file...")

    private val tweetInput = SimpleStringProperty("What's on your mind?")

    private val replyStatusInput = SimpleStringProperty("")

    override val root = form {
        fieldset {

            field {
                textarea(tweetInput)
            }

            field("In Reply to") {
                textfield(replyStatusInput)
            }

            text(mediaPath)

            button("Add Media") {
                action {
                    val files = chooseFile("Choose Media", mediaExtensions, FileChooserMode.Single, this@MainView.currentWindow)
                    if (files.isNotEmpty()) {
                        mediaPath.set(files[0].absolutePath)
                        file = files[0]
                    }
                }
            }

            button("Remove Media") {
                action {
                    file = null
                    mediaPath.set("No media file...")
                }
            }

            button("Tweet") {
                action {
                    status.set("Sending tweet")
                    if (file != null) {
                        if (replyStatusInput.value.isNotEmpty())
                            status.set(twitterClient.replyToTweet(tweetInput.value, file!!, replyStatusInput.value.toLong()))
                        else
                            status.set(twitterClient.tweet(tweetInput.value, file!!))
                    } else {
                        if (replyStatusInput.value.isNotEmpty())
                            status.set(twitterClient.replyToTweet(tweetInput.value, replyStatusInput.value.toLong()))
                        else
                            status.set(twitterClient.tweet(tweetInput.value))
                    }

                }
            }

            text(status)
        }
    }

    companion object {

        val mediaExtensions = arrayOf(
            FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.gif"),
            FileChooser.ExtensionFilter("videos", "*.mp4", "*.avi")
        )

    }
}