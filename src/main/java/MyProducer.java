import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args){
        Properties prop = new Properties();
        //wziete z http://kafka.apache.org/documentation/#configuration (3.3 producer configs)
        prop.setProperty("bootstrap.servers","127.0.0.1:9092");
        prop.setProperty("key.serializer",StringSerializer.class.getName());
        prop.setProperty("value.serializer",StringSerializer.class.getName());
        prop.setProperty("acks","1");
        prop.setProperty("retries", "3"); //jesli nie uda sie wypchnąć to probuj 3x
        prop.setProperty("linger.ms", "1"); //co 1 milisek wysylaj komunikaty(producerRecord)

        //Producer<String, String>: 1wszy String bo key.serializer jest Stringiem, 2gi String bo value.serializer tez Stringiem
        Producer<String, String> producer = new KafkaProducer<String, String>(prop);

        //wartosc key=3 gwarantuje nam, ze message pojdzie na te samą partycje
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("myTopicFromJava",
//                "3", "second message for myTopicFromJava");
//        producer.send(producerRecord);

        for (int key=140; key<=170; key++){
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic",
                    Integer.toString(key), "message for key="+ Integer.toString(key));
            producer.send(producerRecord);
        }


        producer.close(); //zeby nie zostawiac otwartych polaczen


    }
}
