package ai.si_analytics

import io.grpc.ManagedChannelBuilder

fun main(args: Array<String>) {

    println("[client] main()")
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 10004)
        .usePlaintext()
        .build()

    val sampleRequest = getHelloRequest("Hello from client", "Jho")

    val stub = HelloServiceGrpc.newBlockingStub(channel)
    val responseSayHello = stub.sayHello(sampleRequest)
    val responseLotsOfReplies = stub.lotsOfReplies(sampleRequest)

    println("[client] responseSayHello(reply: ${responseSayHello.reply}, name: ${responseSayHello.name})")
    responseLotsOfReplies.forEach {
        println("[client] responseLotsOfReplies(reply: ${it.reply}, name: ${it.name}")
    }
}


fun getHelloRequest(greeting: String, name: String): Hello.HelloRequest {
    return Hello.HelloRequest.newBuilder()
        .setGreeting(greeting)
        .setName(name)
        .build()
}