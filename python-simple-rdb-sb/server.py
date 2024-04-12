from flask import Flask
import db

app = Flask(__name__)


@app.route('/')
def hello_world():
    return db.return_url


if __name__ == '__main__':
    app.run()
