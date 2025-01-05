import json
import time

# Path to the shared JSON file
data_file = "../Shared/data.json"

while True:
    try:
        # Read the data from the JSON file
        with open(data_file, "r") as file:
            data = json.load(file)

        # Check if there is a request for addition
        if data.get("operation") == "add":
            num1 = data.get("num1", 0)
            num2 = data.get("num2", 0)
            result = num1 + num2

            # Write the result back to the JSON file
            data["result"] = result
            data["operation"] = "done"  # Mark the operation as done

            with open(data_file, "w") as file:
                json.dump(data, file)
    except Exception as e:
        print(f"Error: {e}")

    # Sleep briefly to prevent excessive file access
    time.sleep(0.1)
