package com.aceinteract.twitter.cli

import tornadofx.launch

fun main(args: Array<String>) {
    println("Hello Bot!")

    launch<TwitterApp>(args)
}