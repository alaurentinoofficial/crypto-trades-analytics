package com.something.binancewebsocket

import com.binance.connector.client.impl.WebsocketClientImpl
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main(args: Array<String>) {
    // Kafka configs
    val properties = Properties()
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    val producer: KafkaProducer<String, String> = KafkaProducer<String, String>(properties)

    // Binance Websocket
    val client = WebsocketClientImpl()
    val streams = ArrayList<String>()
    streams.add("btcusdt@trade")
    streams.add("ethusdt@trade")

    client.combineStreams(streams) { event ->
        producer.send(ProducerRecord("binance_stream_events", event))
        println(event)
    }
}