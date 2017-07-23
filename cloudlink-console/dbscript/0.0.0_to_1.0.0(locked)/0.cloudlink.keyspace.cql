-- 云链 keyspace
CREATE KEYSPACE if not exists cloudlink WITH replication = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};
