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

    /**
     * Retrieve data from MySQL database
     */
    public Optional<DataEntity> getDataFromDB(String key) {
        return dataRepository.findByKey(key);
    }

    /**
     * Save data to MySQL database
     */
    public DataEntity saveData(String key, String value) {
        DataEntity entity = new DataEntity();
        entity.setKey(key);
        entity.setValue(value);
        return dataRepository.save(entity);
    }

    /**
     * Delete data from MySQL database
     */
    public void deleteData(String key) {
        dataRepository.findByKey(key).ifPresent(dataRepository::delete);
    }
}
