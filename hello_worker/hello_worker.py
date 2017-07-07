import os
import json
from celery import Celery

env_vars = os.getenv('VCAP_SERVICES', "{}")
rmq_service = str(os.getenv('RMQ_SERVICE', 'p-rabbitmq'))
service = json.loads(env_vars)[rmq_service][0]
amqp_url = service['credentials']['protocols']['amqp']['uri']

print amqp_url
app = Celery('hello_worker', backend='rpc://', broker=amqp_url)

@app.task
def add(x, y):
    return x + y