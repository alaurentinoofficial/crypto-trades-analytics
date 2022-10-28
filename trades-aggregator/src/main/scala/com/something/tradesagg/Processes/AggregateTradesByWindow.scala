package com.something.tradesagg.Processes

import com.something.tradesagg.Models.{AggCoinPrice, BinanceTradeEvent}
import org.apache.flink.streaming.api.scala.function._
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

class AggregateTradesByWindow extends WindowFunction[BinanceTradeEvent, AggCoinPrice, String, TimeWindow] {
  override def apply(symbol: String,
                     window: TimeWindow,
                     elements: Iterable[BinanceTradeEvent],
                     out: Collector[AggCoinPrice]): Unit = {
    if (elements.isEmpty) return
    
    var minPrice = -1d
    var maxPrice = -1d
    var count = 0
    val prices: ListBuffer[Double] = ListBuffer.empty

    for (i <- elements) {
      val price = i.p.toDouble
      prices += price

      if (price < minPrice || minPrice == -1) minPrice = price
      if (price > maxPrice || maxPrice == -1) maxPrice = price

      count += 1
    }

    out.collect(
      AggCoinPrice(
        symbol,
        prices.sum / count,
        minPrice, maxPrice,
        count,
        window.getStart, window.getEnd,
        System.currentTimeMillis()))
  }
}