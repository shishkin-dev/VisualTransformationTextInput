package com.example.visualtransformationtextinput.di

import com.example.visualtransformationtextinput.viewModel.MainActivityViewModel
import com.example.visualtransformationtextinput.mapper.TaggedTextMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factoryOf(::TaggedTextMapper)
    viewModelOf(::MainActivityViewModel)
}