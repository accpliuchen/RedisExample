package com.example.redisapp.service;

import com.example.redisapp.entity.DataEntity;
import com.example.redisapp.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseService {

    @Autowired
    private DataRepository dataRepository;

    public Optional<DataEntity> getDataFromDB(String key) {
        return dataRepository.findByKey(key);
    }

    public DataEntity saveData(String key, String value) {
        DataEntity entity = new DataEntity();
        entity.setKey(key);
        entity.setValue(value);
        return dataRepository.save(entity);
    }

    public void deleteData(String key) {
        dataRepository.findByKey(key).ifPresent(dataRepository::delete);
    }
}
