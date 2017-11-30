package com.horacio.Repository;

import com.horacio.Model.Admin;
import com.horacio.Model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Horac on 2017/5/15.
 */
public interface RecordRepository extends JpaRepository<Record, Integer> {



}
