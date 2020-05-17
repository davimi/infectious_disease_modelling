package com.github.davimi

object Utils {

    fun Double?.renderWithPrefix(prefix: String): String {
        if (this == null){
            return ""
        } else {
            return prefix + this.toString()
        }
    }
}