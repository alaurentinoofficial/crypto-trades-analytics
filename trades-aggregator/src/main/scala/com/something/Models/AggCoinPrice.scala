package com.something.Models

case class AggCoinPrice(symbol: String,
                        price: Double,
                        minPrice: Double,
                        maxPrice: Double,
                        tradesQuantity: Int,
                        startTs: Long,
                        endTs: Long,
                        partitionTs: Long)
