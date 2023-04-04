package com.rehat.rehatcoffee.domain.menu.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuUseCase @Inject constructor(
    private val menuRepository: MenuRepository
) {
    suspend fun getMenuDrink(): Flow<BaseResult<List<MenuEntity>, WrappedListResponse<MenuResponse>>> {
        return menuRepository.getMenuDrink()
    }

    suspend fun getMenuFood(): Flow<BaseResult<List<MenuEntity>, WrappedListResponse<MenuResponse>>> {
        return menuRepository.getMenuFood()
    }
}