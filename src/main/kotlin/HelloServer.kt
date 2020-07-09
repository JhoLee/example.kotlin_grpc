package ai.si_analytics

import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

fun main(args: Array<String>) {

    println("[server] main()")
    val service = HelloService()
    val server = ServerBuilder
        .forPort(10004)
        .addService(service)
        .build()

    println("[server] server starts()")
    server.start()
    server.awaitTermination()
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
        println("[server] lotsOfReplies()")
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