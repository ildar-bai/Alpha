package com.baishev.alpha.repos;

import com.baishev.alpha.domain.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, Long> {
    List<Item> findByName(String name);
}
