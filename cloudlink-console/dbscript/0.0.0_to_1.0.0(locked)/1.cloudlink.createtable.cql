-- 区域数据中心
CREATE TABLE if not exists cloudlink_area (
	"code" text,
	name text,
	remark text,
	"mq_wan_addr" text,
	"mq_mgmt_addr" text,
	PRIMARY KEY ("code")
);

-- 系统类型
CREATE TABLE if not exists cloudlink_app_type (
	"code" text,
	name text,
	PRIMARY KEY ("code")
);

-- 所有队列信息
CREATE TABLE if not exists cloudlink_app_queue (
	name text,
	sender text,
	receiver text,
	"message_type" text,
	remark text,
	"max_msg" int,
	emails set<text>,
	PRIMARY KEY (name)
);
CREATE INDEX if not exists app_queue_sender_key ON cloudlink_app_queue (sender);
CREATE INDEX if not exists app_queue_receiver_key ON cloudlink_app_queue (receiver);
CREATE INDEX if not exists app_queue_message_type_key ON cloudlink_app_queue ("message_type");


-- appId
CREATE TABLE if not exists cloudlink_app_id (
	"app_id" text,
	area text,
	country text,
	"app_type" text,
	"unique_id" text,
	enable boolean,
	remark text,
	PRIMARY KEY ("app_id")
);

CREATE INDEX if not exists app_id_app_type_key ON cloudlink_app_id ("app_type");
CREATE INDEX if not exists app_id_area_key ON cloudlink_app_id (area);
CREATE INDEX if not exists app_id_unique_id ON cloudlink_app_id ("unique_id");
CREATE INDEX if not exists app_id_enable ON cloudlink_app_id ("enable");

-- 系统默认配置
CREATE TABLE if not exists cloudlink_system_config (
	key text,
	value text,
	PRIMARY KEY (key)
);

-- 告警配置
CREATE TABLE if not exists cloudlink_alarm_config (
	id text,
	"alarm_type" text,
	area set<text>,
	"alarm_cron" text,
	"alarm_way" set<text>,
	"alarm_email" text,
	"alarm_settings" MAP<text, text>,
	enabled boolean,
	PRIMARY KEY (id)
);
CREATE INDEX if not exists alarm_config_alarm_type_key ON cloudlink_alarm_config ("alarm_type");

-- 用户鉴权表
CREATE TABLE if not exists cloudlink_user_permission (
	username text,
	password text,
	rolename set<text>,
	"token" text,
	token_expire bigint,
	PRIMARY KEY (username)
);
CREATE INDEX if not exists user_permission_token ON cloudlink_user_permission ("token");

-- 待发邮件表
CREATE TABLE if not exists cloudlink_mail(
	id text,
	receivers SET<text>,
	subject text,
	content text,
	send_success boolean,
	date timestamp,
	PRIMARY KEY (id)
);
CREATE INDEX if not exists mail_send_success ON cloudlink_mail ("send_success");
