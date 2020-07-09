package ai.si_analytics

import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.lang.Thread.sleep

fun main(args: Array<String>) {

    println("[server] main()")
    val service = HelloService()
    val server = ServerBuilder
        .forPort(10004)
        .addService(service)
        .build()

    println("[server] server starts")
    server.start()
    server.awaitTermination()
    println("[server] started.")
}

class HelloService : HelloServiceGrpc.HelloServiceImplBase() {

    override fun sayHello(request: Hello.HelloRequest?, responseObserver: StreamObserver<Hello.HelloResponse>?) {
        println("[server] sayHello(${request?.greeting}, ${request?.name})")

        val response = Hello.HelloResponse.newBuilder()
            .setReply("request from '${request?.name}' was \"${request?.greeting}\"")
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun lotsOfReplies(request: Hello.HelloRequest?, responseObserver: StreamObserver<Hello.HelloResponse>?) {
        println("[server] lotsOfReplies(${request?.greeting}, ${request?.name})")

        for (i in 1..4) {
            val response = Hello.HelloResponse.newBuilder()
                .setReply("Hello - $i")
                .setName("${request?.name}")
                .build()
            responseObserver?.onNext(response)
            sleep(1000)
        }
        responseObserver?.onCompleted()
    }

    override fun lotsOfGreetings(responseObserver: StreamObserver<Hello.HelloResponse>?): StreamObserver<Hello.HelloRequest> {
        println("[server] lotsOfGreetings()")
        return super.lotsOfGreetings(responseObserver)
    }

    override fun bidiHello(responseObserver: StreamObserver<Hello.HelloResponse>?): StreamObserver<Hello.HelloRequest> {
        println("[server] bidiHello()")
        return super.bidiHello(responseObserver)
    }
}