package ai.si_analytics

import io.grpc.ManagedChannelBuilder

fun main(args: Array<String>) {

    println("[client] main()")
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 10004)
        .usePlaintext()
        .build()

    val stub = HelloServiceGrpc.newBlockingStub(channel)
    val response = stub.sayHello(getHelloRequest("hello from client", "Jho"))

    println("[client] response(${response.reply})")
}

fun getHelloRequest(greeting: String, name: String): Hello.HelloRequest {
    return Hello.HelloRequest.newBuilder()
        .setGreeting(greeting)
        .setName(name)
        .build()
}