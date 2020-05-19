# alura-kafka
Repositório para cursos relacionados ao kafka feitos na plataforma Alura

## Parte 1: Introdução a Streams em Microserviços

*Comandos Kakfa*

 - Teste da instalação do binário(comandos para subir zookeeper e kafka-server e suas configs)
 
    - bin/zookeeper-server-start.sh config/zookeeper.properties (sobe o zooleeper)
    - bin/kafka-server-start.sh config/server.properties (sobe o kafka na porta padrão 9092)

     Comandos: Tópicos
    - bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic NOME_TOPICO 
         (cria um tópico no servidor do kafka com 1 partição)
    - bin/kafka-topics.sh --list --bootstrap-server localhost:9092 (lista os tópicos no servidor)
    - bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic ECOMMERCE_NEW_ORDER --partitions 3
         (Altera o número de partições de um tópico que já foi criado)
    - bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic ECOMMERCE_NEW_ORDER --partitions 3 replication-factor 2
         (Altera o fator de replicação do tópico para usar uma réplica do broker caso algum broker caia)
         Obs.: Esse comando --alter não pode ser usado para alterar o fator de replicação, esse atributo só pode ser setado na criação dos tópicos.

     Comandos: Producer
    - bin/kafka-console-producer.sh --broker-list localhost:9092 --topic NOME_TOPICO (criar um producer no tópico indicado)

     Comandos: Consumer
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NOME_TOPICO --from-beginning 
         (cria um consumer que consome do tópico indicado desde o início)
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NOME_TOPICO
         (cria um consumer que consome do tópico indicado da partir do momento de sua criação)
    - bin/kafka-consumer-groups.sh --all-groups --bootstrap-server localhost:9092 --describe
         (exibe a situação de cada grupo de consumo)

     Comandos: Consumer Groups
    - kafka-consumer-groups --bootstrap-server localhost:9092 --group app --reset-offsets --execute --topic fifth_topic --shift-by -2 
      (reseta o offset de consumo voltando 2)

*Conceitos*

Qual a importância das chaves na paralelização de tarefas?
 - A chave é usada para distribuir a mensagem entre as partições existentes e consequentemente entre as instâncias de um serviço dentro de um consumer group.

 *Properties*

 ACKS_CONFIG: 0, 1 ou all
  - propriedade que permite um pouco mais de reliability para as mensagens. Se configurado com "all", significa que vai escrever na replica/maquina/broker Leader e vai aguardar a escrita nas demais réplicas que estiverem in-sync. Essa configuração faz sentido setar com valor all sempre que tiver mais de um broker rodando.
    

