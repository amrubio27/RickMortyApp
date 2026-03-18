package org.amrubio27.rickmortyapp.di

import org.amrubio27.rickmortyapp.data.database.RickMortyDatabase
import org.amrubio27.rickmortyapp.data.database.getDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<RickMortyDatabase> { getDatabase(get()) }
    }
}
