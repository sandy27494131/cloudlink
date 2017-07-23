import logging

import pika

LOG_FORMAT = ('%(levelname) -10s %(asctime)s %(name) -30s %(funcName) '
              '-35s %(lineno) -5d: %(message)s')
logging.basicConfig(level=logging.INFO, format=LOG_FORMAT)
LOGGER = logging.getLogger(__name__)

index = 0


def on_message(channel, method_frame, header_frame, body):
    channel.basic_ack(delivery_tag=method_frame.delivery_tag)
    current_index = int(body.split(':')[1])
    interval = current_index - index
    if interval > 1:
        print "current_index:%d" % current_index
        print 'interval:%d' % interval
        print
    if interval == 0:
        print "current_index:%d" % current_index
        print 'interval:%d' % interval
        print
    global index
    index = current_index


parameters = pika.URLParameters(
    'amqp://admin:winit2015@usmq.staging.winit.com.cn:5672/%2F?connection_attempts=3&heartbeat_interval=3600&socket_timeout=1000')
connection = pika.BlockingConnection(parameters)
channel = connection.channel()
channel.basic_consume(on_message, 'TEST_QUEUE')
try:
    channel.start_consuming()
except KeyboardInterrupt:
    channel.stop_consuming()
connection.close()
