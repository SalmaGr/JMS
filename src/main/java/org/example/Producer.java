package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Un producteur simple de messages JMS qui se connecte à ActiveMQ et envoie un message texte à un sujet.
 */
public class Producer {
    public static void main(String[] args) {
        try {
            // Établissement de la connexion avec le courtier ActiveMQ
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Création de la session et du sujet pour la production de messages
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("myTopic.topic"); // Spécifiez le nom et le type du sujet
            MessageProducer producer = session.createProducer(destination);

            // Création et envoi d'un message texte
            TextMessage message = session.createTextMessage();
            message.setText("Bonjour le monde !");
            producer.send(message);

            // Validation de la session et fermeture des ressources
            session.commit();
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
