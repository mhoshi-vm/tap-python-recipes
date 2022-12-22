import postgresql
from pyservicebinding import binding
from typing import Any


def main(req: Any):
    host = ""
    port = ""
    database = ""
    username = ""
    password = ""

    try:
        sb = binding.ServiceBinding()
        bindings_list = sb.bindings("postgresql")
        if not bindings_list[0]:
            host = bindings_list[0].get("host")
            port = bindings_list[0].get("port")
            database = bindings_list[0].get("database")
            username = bindings_list[0].get("username")
            password = bindings_list[0].get("password")
    except binding.ServiceBindingRootMissingError as msg:
        # log the error message and retry/exit
        print("SERVICE_BINDING_ROOT env var not set")

    db_url = 'pq://' + username + ":" + password + "@" + host + ":" + port + "/" + database
    db = postgresql.open()
    print(db_url)
    get_table = db.prepare("SELECT * from information_schema.tables WHERE table_name = $1")
    return print(get_table("tables"))
