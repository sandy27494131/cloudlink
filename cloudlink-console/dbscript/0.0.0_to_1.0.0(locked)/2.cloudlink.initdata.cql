insert into cloudlink_system_config(key,value) values ('ALARM_WAY','email');
insert into cloudlink_system_config(key,value) values ('ALARM_EMAIL','jianke.zhang@winit.com.cn');

insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('CLUSTER_NODE_STATUS','CLUSTER_NODE_STATUS','0 0/30 * * * ?','',{},{'EMAIL'},{},false);
insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('NODE_WORKER_STATUS','NODE_WORKER_STATUS','0 0/30 * * * ?','',{},{'EMAIL'},{},false);
insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('CONNECTIONS','CONNECTIONS','0 0/30 * * * ?','',{'CHANNELS_THRESHOLD': '1000', 'CONNECTIONS_THRESHOLD': '200'},{'EMAIL'},{},false);
insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('EXCHANGE_STATUS','EXCHANGE_STATUS','0 0/30 * * * ?','',{},{'EMAIL'},{},false);
insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('QUEUE_STATUS','QUEUE_STATUS','0 0/30 * * * ?','',{'QUEUE_MESSAGE_AMOUNT_THRESHOLD': '1000', 'QUEUE_MESSAGE_IS_CONSUMED': 'true'},{'EMAIL'},{},false);
insert into cloudlink_alarm_config(id,alarm_type,alarm_cron,alarm_email,alarm_settings,alarm_way,area,enabled) values ('SHOVEL_STATUS','SHOVEL_STATUS','0 0/30 * * * ?','',{},{'EMAIL'},{},false);

insert into cloudlink_user_permission(username,password,rolename) values ('admin','991DE41BEAC7D7C1A3C4B888AA1611F3',{'ADMIN'});

insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('CHECK','OMS','CWM','COMMAND','查验指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('COST','TMS','SMS','COMMAND','成本记录指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('DEDUCTION','OMS','CWM','COMMAND','扣货指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('DELIVERY','OMS','CWM','COMMAND','发货指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('EXPORT_CLEARANCE','OMS','TMS','COMMAND','出口报关指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('IMPORT_CLEARANCE','OMS','TMS','COMMAND','进口报关指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('INSPECTION','OMS','CWM','COMMAND','验货指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('LOGISTICS','OMS','TMS','COMMAND','物流指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('PACKING','OMS','CWM','COMMAND','打包指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('PICKUP','OMS','TMS','COMMAND','提货指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('RECEPTION','OMS','CWM','COMMAND','收货指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('REVENUE','OMS','SMS','COMMAND','定单收入指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('ReceptionPackage','CWM','CWM','COMMAND','收货指令单包裹');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('SENDPORT','OMS','TMS','COMMAND','送港指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('SHELVE','OMS','CWM','COMMAND','上架指令类型');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('ims_item_channel','IMS','IMS','COMMAND','单品全球同步');
insert into cloudlink_app_queue(name,sender,receiver,"message_type",remark) values ('SENDWAREHOUSE','OMS','TMS','COMMAND','送仓指令类型');

insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNCAN.CWM.CN.CNR','CWM','CNR','CN',true,'','CNCAN');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNHKG.CWM.CN.CNR','CWM','CNR','CN',true,'','CNHKG');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNNGB.CWM.CN.CNR','CWM','CNR','CN',true,'','CNNGB');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNSHA.CWM.CN.CNR','CWM','CNR','CN',true,'','CNSHA');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNSZX.CWM.CN.CNR','CWM','CNR','CN',true,'','CNSZX');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('CNTAO.CWM.CN.CNR','CWM','CNR','CN',true,'','CNTAO');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('OMS1.OMS.ALL.CNR','OMS','CNR','ALL',true,'','OMS1');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('SMS1.SMS.ALL.CNR','SMS','CNR','ALL',true,'','SMS1');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('TMS1.TMS.ALL.CNR','TMS','CNR','ALL',true,'','TMS1');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('IMS1.IMS.ALL.CNR','IMS','CNR','ALL',true,'','IMS1');

insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('USKY.CWM.US.USR','CWM','USR','US',true,'','USKY');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('USLA.CWM.US.USR','CWM','USR','US',true,'','USLA');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('IMS2.IMS.ALL.USR','IMS','USR','ALL',true,'','IMS2');

insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('DEBRE.CWM.DE.EUR','CWM','EUR','DE',true,'','DEBRE');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('UKLE.CWM.UK.EUR','CWM','EUR','UK',true,'','UKLE');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('UKMA.CWM.UK.EUR','CWM','EUR','UK',true,'','UKMA');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('IMS3.IMS.ALL.EUR','IMS','EUR','ALL',true,'','IMS3');

insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('AUSYD.CWM.AU.AUR','CWM','AUR','AU',true,'','AUSYD');
insert into cloudlink_app_id(app_id,app_type,area,country,enable,remark,unique_id) values ('IMS4.IMS.ALL.AUR','IMS','AUR','ALL',true,'','IMS4');
