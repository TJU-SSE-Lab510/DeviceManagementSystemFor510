package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Facility;
import com.horacio.Model.Record;
import com.horacio.Repository.FacilityRepository;
import com.horacio.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private FacilityRepository facilityRepository;



    @Transactional
    public void add(JsonNode data) throws Exception{
            String itemName =  data.get("itemName").textValue();
            Facility item =facilityRepository.findOneByItemName(itemName);
            if(item == null){
                throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
            }
            int remianNum = item.getRemainNum();
            remianNum = remianNum - data.get("number").intValue();
            if(remianNum < 0){
                throw new LabsException(ResultEnum.FACILITY_NOT_ENOUGH.getCode(),ResultEnum.FACILITY_NOT_ENOUGH.getMsg());
            }
            item.setRemainNum(remianNum);
            facilityRepository.save(item);
            Record record = new Record();
            record.setItemName(data.get("itemName").textValue());
            record.setBorrowedTime(new Date(Long.valueOf(data.get("borrowedTime").textValue())));
            record.setName(data.get("name").textValue());
            record.setPhone(data.get("phone").textValue());
            record.setBorrowOperator(data.get("borrowOperator").textValue());
            record.setNumber(data.get("number").intValue());
            recordRepository.save(record);
    }

    @Transactional
    public Boolean edit(JsonNode data) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        if(record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        record.setName(data.get("name").textValue());
        record.setPhone(data.get("phone").textValue());
        recordRepository.save(record);
        return true;
    }

    @Transactional
    public Boolean returnItem(JsonNode data) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        Facility item =facilityRepository.findOneByItemName(record.getItemName());
        if(item == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        record.setReturnTime(new Date(Long.valueOf(data.get("returnTime").intValue())));
        record.setReturnOperator(data.get("returnOperator").textValue());
        recordRepository.save(record);
        int remianNum = item.getRemainNum();
        remianNum = remianNum + record.getNumber();
        item.setRemainNum(remianNum);
        facilityRepository.save(item);
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
