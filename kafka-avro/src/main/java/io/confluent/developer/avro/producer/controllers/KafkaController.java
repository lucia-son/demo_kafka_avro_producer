package io.confluent.developer.avro.producer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.confluent.developer.Album;
import io.confluent.developer.Song;
import io.confluent.developer.avro.producer.engine.Producer;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/send")
@CommonsLog(topic = "Controller Logger")
public class KafkaController {

    private final Producer producer;
    private List<Song> songList = new ArrayList<>();

    @Autowired
    KafkaController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/album")
    public void sendMessage(@RequestBody ObjectNode body) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.treeToValue(body.get("Album"), Album.class);

        this.producer.sendAlbum(album);
    }

}