-- 队列告警配置信息
CREATE TABLE if not exists cloudlink_queue_alarm (
	name text,
	remark text,
	"max_msg" int,
	emails set<text>,
	mobile text,
	disabled boolean,
	PRIMARY KEY (name)
);

alter table cloudlink_alarm_config add mobile text;
