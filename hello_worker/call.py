from flask import Flask
import json
import os
from celery import Celery
from celery.execute import send_task

app = Flask(__name__)

@app.route("/<int:first_number>/<int:second_number>")
def call(first_number, second_number):
    env_vars = os.getenv('VCAP_SERVICES', "{}")
    rmq_service = str(os.getenv('RMQ_SERVICE', 'p-rabbitmq'))
    service = json.loads(env_vars)[rmq_service][0]
    amqp_url = service['credentials']['protocols']['amqp']['uri']

    celery_app = Celery('hello_worker', backend='rpc://', broker=amqp_url)
    result = send_task('hello_worker.add', (first_number, second_number))
    return "I added some numbers: %s + %s = %s" % (first_number, second_number, result.get(timeout=30))


if __name__ == "__main__":
    PORT = os.environ.get('PORT', 8080)
    HOST = "0.0.0.0"
    app.run(HOST, PORT)