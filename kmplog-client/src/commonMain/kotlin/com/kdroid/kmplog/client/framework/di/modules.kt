package com.kdroid.kmplog.client.framework.di

import com.kdroid.kmplog.client.data.local.HomePreferencesRepositoryImpl
import com.kdroid.kmplog.client.data.local.SettinsPreferencesRepositoryImpl
import com.kdroid.kmplog.client.data.network.WebSocketManager
import com.kdroid.kmplog.client.data.network.engine
import com.kdroid.kmplog.client.domain.HomePreferencesRepository
import com.kdroid.kmplog.client.domain.SettingsPreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.DefaultNavigator
import com.kdroid.kmplog.client.presentation.navigation.Destination
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import com.kdroid.kmplog.client.presentation.screens.home.HomeViewModel
import com.kdroid.kmplog.client.presentation.screens.settings.SettingsViewModel
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { DefaultNavigator(startDestination = Destination.Home) }

    single { Settings() }
    single<HomePreferencesRepository> { HomePreferencesRepositoryImpl(settings = get()) }
    single<SettingsPreferencesRepository>{ SettinsPreferencesRepositoryImpl(settings = get()) }
    single {  WebSocketManager(settingsRepository = get()) }

    viewModel { HomeViewModel(engine, navigator= get(), repository = get(), webSocketManager = get()) }
    viewModel { SettingsViewModel(repository = get(), navigator = get()) }


}

fun initKoin(modules: Module = appModule) =
    startKoin {
        modules(modules = modules)
    }
