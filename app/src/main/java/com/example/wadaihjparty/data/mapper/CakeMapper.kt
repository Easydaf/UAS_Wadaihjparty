package com.example.wadaihjparty.data.mapper

import com.example.wadaihjparty.data.remote.dto.CakeDto
import com.example.wadaihjparty.domain.model.Cake

fun CakeDto.toDomain(): Cake {
    return Cake(
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        price = this.price
    )
}

fun List<CakeDto>.toDomain(): List<Cake> {
    return this.map { it.toDomain() }
}