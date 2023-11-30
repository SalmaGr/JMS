package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) {
        try {
            // Étape 1 : Initialiser la création d'un objet ConnectionFactory avec l'URL tcp://localhost:61616
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Étape 2 : Créer une connexion en utilisant l'objet ConnectionFactory
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Étape 3 : Initialiser la création d'une session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Étape 4 : Créer une destination (Topic)
            Destination destination = session.createTopic("myTopic.topic");

            // Étape 5 : Créer un objet MessageConsumer associé à la session et à la destination
            MessageConsumer consumer = session.createConsumer(destination);

            // Étape 6 : Démarrer la connexion
            connection.start();

            // Étape 7 : Mettre en place un JMS Listener pour la réception asynchrone des messages
            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage textMessage = (TextMessage) message;
                            System.out.println("Message reçu : " + textMessage.getText());
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };

            consumer.setMessageListener(listener);

            // Attente pour permettre au listener de recevoir des messages de manière asynchrone
            Thread.sleep(30000);

            // Fermer les ressources
            consumer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
