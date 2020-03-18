# alura-kafka
Repositório para cursos relacionados ao kafka feitos na plataforma Alura

## Parte 1: Introdução a Streams em Microserviços

*Comandos Kakfa*

 - Teste da instalação do binário(comandos para subir zookeeper e kafka-server e suas configs)
 
    - bin/zookeeper-server-start.sh config/zookeeper.properties (sobe o zooleeper)
    - bin/kafka-server-start.sh config/server.properties (sobe o kafka na porta padrão 9092)
    - bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic NOME_TOPICO 
         (cria um tópico no servidor do kafka com 1 partição)
    - bin/kafka-topics.sh --list --bootstrap-server localhost:9092 (lista os tópicos no servidor)
    - bin/kafka-console-producer.sh --broker-list localhost:9092 --topic NOME_TOPICO (criar um producer no tópico indicado)
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NOME_TOPICO --from-beginning 
         (cria um consumer que consome do tópico indicado desde o início)
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NOME_TOPICO
         (cria um consumer que consome do tópico indicado da partir do momento de sua criação)