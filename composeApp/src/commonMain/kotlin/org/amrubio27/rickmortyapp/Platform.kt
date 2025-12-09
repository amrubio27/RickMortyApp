package org.amrubio27.rickmortyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform