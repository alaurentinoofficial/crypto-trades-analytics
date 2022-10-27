package com.something.Models

case class BinanceTradeEvent(
  e: String, // Event type
  E: Long, // Event time
  s: String, // Symbol
  a: Long, // Aggregate trade ID
  p: String, // Price
  q: String, // Quantity
  f: Long, // First trade ID
  l: Long, // Last trade ID
  T: Long, // Trade time
  m: Boolean, // Is the buyer the market maker?
  M: Boolean // Ignore
)
