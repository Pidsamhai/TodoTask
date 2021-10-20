package com.github.psm.todo

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}