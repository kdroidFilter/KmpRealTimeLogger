package com.kdroid.kmplog.client.framework.di

import com.kdroid.kmplog.client.data.local.PreferencesRepositoryImpl
import com.kdroid.kmplog.client.data.network.engine
import com.kdroid.kmplog.client.domain.PreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.DefaultNavigator
import com.kdroid.kmplog.client.presentation.navigation.Destination
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import com.kdroid.kmplog.client.presentation.screens.home.HomeViewModel
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { DefaultNavigator(startDestination = Destination.Home) }

    single { Settings() }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    viewModel { HomeViewModel(engine, navigator= get(), preferencesRepository = get()) }


}

fun initKoin(modules: Module = appModule) =
    startKoin {
        modules(modules = modules)
    }
