package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Record;
import com.horacio.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;




    @Transactional
    public Boolean add(JsonNode data) throws Exception{
            Record record = new Record();
            record.setItemName(data.get("itemName").textValue());
            record.setBorrowedTime(data.get("borrowedTime").intValue());
            record.setName(data.get("name").textValue());
            record.setPhone(data.get("phone").textValue());
            record.setBorrowOperator(data.get("borrowOperator").textValue());
            recordRepository.save(record);
            return true;
    }

    @Transactional
    public Boolean edit(JsonNode data) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        record.setName(data.get("name").textValue());
        record.setPhone(data.get("phone").textValue());
        recordRepository.save(record);
        return true;

    }

    @Transactional
    public Boolean returnItem(JsonNode data) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        record.setReturnTime(data.get("returnTime").intValue());
        record.setReturnOperator(data.get("returnOperator").textValue());
        recordRepository.save(record);
        return true;

    }


    @Transactional
    public List<Record> getAll() throws Exception{
        List<Record> records = recordRepository.findAll();
        return  records;
    }

    @Transactional
    public Boolean delete(JsonNode data) throws Exception{
        Record record = recordRepository.findOne(data.get("id").intValue());
        if (record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }else {
            recordRepository.delete(record);
            return true;
        }

    }

}
