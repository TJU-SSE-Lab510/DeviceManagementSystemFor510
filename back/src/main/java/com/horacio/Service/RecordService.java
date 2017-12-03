package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Facility;
import com.horacio.Model.Record;
import com.horacio.Repository.FacilityRepository;
import com.horacio.Repository.RecordRepository;
import com.horacio.utils.SendThread;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
@Transactional
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SendThread thread;



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
            record.setEmail(data.get("email").textValue());
            record.setBorrowOperator(data.get("borrowOperator").textValue());
            record.setNumber(data.get("number").intValue());
            recordRepository.save(record);
    }

    public Boolean edit(JsonNode data) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        if(record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        record.setName(data.get("name").textValue());
        record.setPhone(data.get("phone").textValue());
        record.setEmail(data.get("email").textValue());
        recordRepository.save(record);
        return true;
    }

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

    public List<Record> getAll() throws Exception{
        List<Record> records = recordRepository.findAll();
        return  records;
    }

    public Boolean delete(JsonNode data) throws Exception{
        Record record = recordRepository.findOne(data.get("id").intValue());
        if (record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }else {
            recordRepository.delete(record);
            return true;
        }

    }


    /**
     * 获取发送的邮件地址并发送邮件
     * @throws Exception
     */
    public void getSendItem() throws Exception{
        Session session = sessionFactory.getCurrentSession();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Criteria criteria = session.createCriteria(Record.class,"record");
        criteria.add(Restrictions.isNull("record.returnTime"));
        String oneMonthAgo = format.format(new Date().getTime()- Long.valueOf("2592000000"));
        criteria.add(Restrictions.lt("record.borrowedTime",format.parse(oneMonthAgo)));
        List<Record> datas = criteria.list();
        for(Record data: datas){
            Date borrowedTime = data.getBorrowedTime();
            long time1 = borrowedTime.getTime();
            long time2 = new Date().getTime();
            int days = (int) Math.floor(Double.valueOf((time2 - time1)/1000/60/60/24));
            //如果是已经借出一个月，或者一个月的倍数则发送邮件
            if(days != 0 && days % 30 == 0 ){
                Facility facility = facilityRepository.findOneByItemName(data.getItemName());
                if(facility != null){
                    String image = facility.getUrl();
                    thread.setImage(image);
                }
                SimpleDateFormat sendformat = new SimpleDateFormat("yyyy-MM-dd");
                String email = data.getEmail();
                String name = data.getName();
                String itemName = data.getItemName();
                String borrowDate = sendformat.format(borrowedTime);
                int month = days/30;
                int number  = data.getNumber();
                thread.setDestination(email);
                thread.setItemName(itemName);
                thread.setName(name);
                thread.setNumber(number);
                thread.setMonth(month);
                thread.setBorrowDate(borrowDate);
                thread.start();
            }
        }
    }


}
