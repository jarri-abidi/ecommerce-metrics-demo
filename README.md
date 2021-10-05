# Getting Started

#### Run prometheus in a container:

`sudo docker run --net="host" -v $PWD/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus`

#### Run the spring boot app.

#### Create some orders:

`curl -H "Content-Type: application/json" -H "Accept: application/json" -X POST --data '{"userEmail":"xyz@gmail.com","items": []}' http://localhost:8080/api/order`

`curl -H "Content-Type: application/json" -H "Accept: application/json" -X POST --data '{"userEmail":"xyz@gmail.com","items": []}' http://localhost:8080/api/order`

#### Dispatch an order:

`curl --request PATCH -H "Content-Type: application/json" --data '{"status": "DISPATCHED"}' http://localhost:8080/api/order/1`

#### Deliver an order:

`curl --request PATCH -H "Content-Type: application/json" --data '{"status": "DELIVERED"}' http://localhost:8080/api/order/1`

#### Cancel an order:

`curl --request PATCH -H "Content-Type: application/json" --data '{"status": "CANCELED"}' http://localhost:8080/api/order/2`

#### Navigate to Prometheus UI in your browser:

`http://localhost:9090`

#

#### Prometheus documentation:

https://prometheus.io/docs/introduction/overview
