package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Admin;
import com.horacio.Model.Facility;
import com.horacio.Model.Record;
import com.horacio.Repository.AdminRepository;
import com.horacio.Repository.FacilityRepository;
import com.horacio.Repository.RecordRepository;
import com.horacio.utils.SendThread;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    private AdminRepository adminRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SendThread thread;

    @Autowired
    private ObjectMapper mapper;



    public void add(String itemName,String borrowedTime,String name,String phone,String email,int number,int type,String userid) throws Exception{
            Facility item =facilityRepository.findOneByItemName(itemName);
            if(item == null){
                throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
            }
            int remianNum = item.getRemainNum();
            remianNum = remianNum - number;
            if(remianNum < 0){
                throw new LabsException(ResultEnum.FACILITY_NOT_ENOUGH.getCode(),ResultEnum.FACILITY_NOT_ENOUGH.getMsg());
            }
            item.setRemainNum(remianNum);
            facilityRepository.save(item);
            Admin user = adminRepository.findOne(Integer.valueOf(userid));
            Record record = new Record();
            record.setType(2);
            record.setItemName(itemName);
            record.setBorrowedTime(new Date(Long.valueOf(borrowedTime)));
            record.setName(name);
            record.setPhone(phone);
            record.setEmail(email);
            record.setBorrowOperator(user.getName());
            record.setNumber(number);
            record.setType(type);
            recordRepository.save(record);
    }

    public Boolean edit(int id,String name,String phone,String email,String userid,int number) throws Exception{
        Record record =recordRepository.findOne(id);
        if(record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        //不允许修改已经完成的借用记录
        if(record.getReturnTime()!=null){
            throw new LabsException(ResultEnum.OPERATE_NOT_ALLOW.getCode(),ResultEnum.OPERATE_NOT_ALLOW.getMsg());
        }
        //不允许修改其他操作人的借用记录
        Admin user = adminRepository.findOne(Integer.valueOf(userid));
        if(!record.getBorrowOperator().equals(user.getName())){
            throw new LabsException(ResultEnum.AUTH_NOT_FOUND.getCode(),ResultEnum.AUTH_NOT_FOUND.getMsg());
        }
        Facility item =facilityRepository.findOneByItemName(record.getItemName());
        if(item == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        int origin  = record.getNumber();
        int change_num = number - origin;
        int remain_num = item.getRemainNum()+change_num;
        if(remain_num < 0){
            throw new LabsException(ResultEnum.FACILITY_NOT_ENOUGH.getCode(),ResultEnum.FACILITY_NOT_ENOUGH.getMsg());
        }
        item.setRemainNum(remain_num);
        facilityRepository.save(item);
        record.setName(name);
        record.setPhone(phone);
        record.setEmail(email);
        record.setNumber(number);
        recordRepository.save(record);
        return true;
    }

    public Boolean returnItem(JsonNode data,String userid) throws Exception{
        Record record =recordRepository.findOne(data.get("id").intValue());
        if(record == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        Facility item =facilityRepository.findOneByItemName(record.getItemName());
        if(item == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        Admin user = adminRepository.findOne(Integer.valueOf(userid));
        record.setReturnTime(new Date(Long.valueOf(data.get("returnTime").textValue())));
        record.setReturnOperator(user.getName());
        recordRepository.save(record);
        int remianNum = item.getRemainNum();
        remianNum = remianNum + record.getNumber();
        item.setRemainNum(remianNum);
        facilityRepository.save(item);
        return true;
    }

    public ArrayNode getAll(int type,String name) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Record.class,"record");
        criteria.add(Restrictions.eq("record.type",type));
        if(type == 1){//分配
            if(name!=null){//若name存在则搜索出分配给该管理员的设备列表
                criteria.add(Restrictions.eq("record.name",name));
            }
        }else{//借出
            if(name!=null){//若name存在则搜索出该管理员的借出的设备列表
                criteria.add(Restrictions.eq("record.borrowed_operator",name));
            }
        }
        criteria.addOrder(Order.desc("record.id"));
        List<Record> datas = criteria.list();
        ArrayNode array = mapper.createArrayNode();
        for(Record item: datas){
            ObjectNode node = mapper.createObjectNode();
            node.put("id",item.getId());
            node.put("borrow_operator",item.getBorrowOperator());
            node.put("borrowed_time",format.format(item.getBorrowedTime()));
            node.put("item_name",item.getItemName());
            node.put("name",item.getItemName());
            node.put("number",item.getNumber());
            node.put("phone",item.getPhone());
            node.put("email",item.getEmail());
            if(item.getReturnTime() == null){
                node.put("return_operator","");
                node.put("return_time","未归还");
            }else{
                node.put("return_operator",item.getReturnOperator());
                node.put("return_time",format.format(item.getReturnTime()));
            }
            array.addPOJO(node);
        }
        return array;
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
        criteria.add(Restrictions.eq("record.type",2));
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
