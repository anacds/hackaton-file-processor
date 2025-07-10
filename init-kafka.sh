#!/bin/bash
set -e

echo "Aguardando Localstack e Kafka ficarem prontos…"
sleep 20

echo "Criando tópicos Kafka"
kafka-topics --bootstrap-server kafka:29092 --create --if-not-exists --topic fhir-success-topic --partitions 1 --replication-factor 1 
kafka-topics --bootstrap-server kafka:29092 --create --if-not-exists --topic fhir-error-topic --partitions 1 --replication-factor 1 

echo "Finalizado com sucesso!"
tail -f /dev/null