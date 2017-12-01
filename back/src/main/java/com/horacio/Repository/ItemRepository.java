package com.horacio.Repository;

import com.horacio.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Horac on 2017/5/15.
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {


}
