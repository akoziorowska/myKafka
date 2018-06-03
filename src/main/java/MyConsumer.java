import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;


public class MyConsumer {
    public static void main(String[] args) {
        Properties prop = new Properties();

        //wziete z http://kafka.apache.org/documentation/#configuration 3.4 Consumer Configs
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer", StringDeserializer.class.getName());
        prop.setProperty("group.id", "myGroupId");
        prop.setProperty("enable.auto.commit", "true"); //choc domyslnie i tak jest true, ale zeby mozna bylo to co ponizej
        prop.setProperty("auto.commit.interval.ms", "1000");//co sekunde offset bedzie comitowany
        prop.setProperty("auto.offset.reset", "earliest"); //automatically reset the offset to the earliest offset

        //<String, String> bo deserializer (i key i value) jest stringiem
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
//        consumer.subscribe(Arrays.asList("myTopicFromJava","first_topic"));
        consumer.subscribe(Arrays.asList("first_topic"));
        while(true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            for(ConsumerRecord<String, String> rec : consumerRecords){
                System.out.println("value="+rec.value()+" key="+rec.key()
                        +" offset"+rec.offset()+" partition="+rec.partition()
                        +" topic="+rec.topic()+" timestamp="+rec.timestamp());

            }
        }

    }
}
