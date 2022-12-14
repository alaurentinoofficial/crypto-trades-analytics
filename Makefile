# EXEMPLE TO GENERATE THE HELM VALUES AND YAMLS
#
# helm inspect values pinot/pinot >> pinot-values.yaml
#
# helm template pinot pinot/pinot \                   
#     --set cluster.name=pinot \
#     --set server.replicaCount=2 >> pinot.yaml
#

install-helm-repos:
	helm repo add pinot https://raw.githubusercontent.com/apache/pinot/master/kubernetes/helm
	helm repo add kafka https://charts.bitnami.com/bitnami
	helm repo add flink-operator-repo https://downloads.apache.org/flink/flink-kubernetes-operator-1.2.0/
	helm repo add superset https://apache.github.io/superset

install-pinot:
	helm install --values pinot/values.yaml pinot pinot/pinot

install-kafka:
	helm install --values kafka/values.yaml kafka kafka/kafka

install-flink-kubernetes-operator:
	kubectl create -f https://github.com/jetstack/cert-manager/releases/download/v1.8.2/cert-manager.yaml
	helm install --values flink-operator/values.yaml flink-kubernetes-operator flink-operator-repo/flink-kubernetes-operator

install-superset:
	helm install --values superset/values.yaml superset superset/superset

setup-trades-aggregator-pinot-table:
	curl -F trades_agg=@trades-aggregator/infra/tables/trades_agg_schema.json localhost:9000/schemas
	sleep 4
	curl -i -X POST -H 'Content-Type: application/json' -d @trades-aggregator/infra/tables/trades_agg_table.json localhost:9000/tables

setup-trades-aggregator:
	docker build ./trades-aggregator -t trades-aggregator
	kubectl apply -f trades-aggregator/infra/flink/job.yaml

build-binance-websocket:
	cd ./binance-websocket/
	sh ./gradlew build

setup-binance-websocket:
	docker build ./binance-websocket -t binance-websocket
	kubectl apply -f binance-websocket/infra/kube/deployment.yaml