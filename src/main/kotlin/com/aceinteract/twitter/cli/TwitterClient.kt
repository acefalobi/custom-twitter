package com.aceinteract.twitter.cli

import twitter4j.StatusUpdate
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.io.File

class TwitterClient {

    private val configuration =  ConfigurationBuilder()
        .setDebugEnabled(true)
        .setOAuthConsumerKey("N4KGbSd4qSa7FZIIJsyFuSmlH")
        .setOAuthConsumerSecret("OR80qqtvNADihfDVA160DrvC3PlPxphpNAbwY41AxterAEY2RX")
        .setOAuthAccessToken("431424329-cN3OEx9SmgfE3ytt7Idjjqu1hrJHNn1MoOq1D8Eu")
        .setOAuthAccessTokenSecret("GqIb2cS3AcW4I8GU04sbhMuNEpXeR0a9hSkr9xib3RwSC")
        .build()

    private val twitterFactory = TwitterFactory(configuration)

    private val twitter = twitterFactory.instance

    fun tweet(tweet: String) = try {
        twitter.updateStatus(tweet)
        "Finished tweeting"
    } catch (e: Exception) {
        "Error tweeting"
    }

    fun tweet(tweet: String, media: File) = try {
        twitter.updateStatus(StatusUpdate(tweet).media(media))
        "Finished tweeting"
    } catch (e: Exception) {
        "Error tweeting"
    }

    fun replyToTweet(tweet: String, mainTweet: Long) = try {
        val originalStatus = twitter.showStatus(mainTweet)
        val statusReply = StatusUpdate(tweet)
        statusReply.inReplyToStatusId = originalStatus.id
        val reply = twitter.updateStatus(statusReply)
        "Posted reply " + reply.id + " in response to tweet " + reply.inReplyToStatusId
    } catch (e: Exception) {
        "Error tweeting"
    }

    fun replyToTweet(tweet: String, media: File, mainTweet: Long) = try {
        val statusReply = StatusUpdate(tweet).inReplyToStatusId(mainTweet).media(media)
        twitter.updateStatus(statusReply)
        "Finished tweeting"
    } catch (e: Exception) {
        "Error tweeting"
    }

}