package com.winit.cloudlink.storage.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.Mail;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
public interface MailRepository extends CrudRepository<Mail, String> {

    @Query("select * from " + TableConstants.TABLE_MAIL + " where send_success = true")
    public List<Mail> findSendSuccess();

    @Query("select * from " + TableConstants.TABLE_MAIL + " where send_success = false")
    public List<Mail> findSendFail();
}
