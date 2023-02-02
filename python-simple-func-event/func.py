from typing import Any

def main(data: Any, attributes: dict):
    print("Attributes" + str(attributes))
    print("Handled cloudevent!")
    return None, "Handled cloudevent!"
