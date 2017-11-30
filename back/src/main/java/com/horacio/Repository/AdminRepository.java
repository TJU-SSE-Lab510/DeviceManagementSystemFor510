package com.horacio.Repository;

import com.horacio.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Horac on 2017/5/15.
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.username = ?1")
    public Admin findByUsername(String username);


}
