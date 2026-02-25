package org.amrubio27.rickmortyapp.di

import org.amrubio27.rickmortyapp.domain.GetRandomCharacter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetRandomCharacter)
}
