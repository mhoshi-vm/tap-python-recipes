import postgresql
from pyservicebinding import binding


def return_url():
    try:
        sb = binding.ServiceBinding()
        print(sb.all_bindings())
        bindings_list = sb.bindings('postgresql')
        if bindings_list:
            host = bindings_list[0].get("host")
            port = bindings_list[0].get("port")
            database = bindings_list[0].get("database")
            username = bindings_list[0].get("username")
            password = bindings_list[0].get("password")
            db_url = 'pq://' + username + ":" + password + "@" + host + ":" + port + "/" + database
            """
            db = postgresql.open()
            get_table = db.prepare("SELECT * from information_schema.tables WHERE table_name = $1")
            """
    except binding.ServiceBindingRootMissingError as msg:
        # log the error message and retry/exit
        print("SERVICE_BINDING_ROOT env var not set")
    else:
        return db_url
