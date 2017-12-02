package com.horacio.Repository;

import com.horacio.Model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arlex on 2017/12/1.
 */
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    public Facility findOneByItemName(String itemName);
}
