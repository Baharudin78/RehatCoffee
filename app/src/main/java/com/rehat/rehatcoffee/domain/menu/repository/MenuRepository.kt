package com.rehat.rehatcoffee.domain.menu.repository

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun getMenuFood() : Flow<BaseResult<List<MenuEntity>,WrappedListResponse<MenuResponse>>>
    suspend fun getMenuDrink(): Flow<BaseResult<List<MenuEntity>, WrappedListResponse<MenuResponse>>>
}