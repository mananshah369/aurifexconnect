package com.erp.Repository.LineItems;

import com.erp.Model.LineItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineItemsRepository extends JpaRepository<LineItems , Long> {

    List<LineItems> findByMaster_MasterId(long masterId);
}
