import logging
import time

import pika

LOG_FORMAT = ('%(levelname) -10s %(asctime)s %(name) -30s %(funcName) '
              '-35s %(lineno) -5d: %(message)s')
logging.basicConfig(level=logging.DEBUG, format=LOG_FORMAT)
LOGGER = logging.getLogger(__name__)

parameters = pika.URLParameters('amqp://admin:winit2015@10.32.16.40:5672/%2F?connection_attempts=3&heartbeat_interval=3600&socket_timeout=1000')
connection = pika.BlockingConnection(parameters)
channel = connection.channel()

interval = 0.001
message_count = 50000
for i in range(message_count):
    messgae = 'message body :%s' % str(i)
    channel.basic_publish('winit_send',
                          'TEST_QUEUE.TEST3.TEST.ALL.EUR',
                          messgae,
                          pika.BasicProperties(content_type='text/plain',
                                               delivery_mode=2))
    LOGGER.info('Published message # %d', i)
    time.sleep(interval)

connection.close()
