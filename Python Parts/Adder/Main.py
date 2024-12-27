from fastapi import FastAPI
from pydantic import BaseModel

# Create FastAPI instance
app = FastAPI()

# Request model for the adder
class Numbers(BaseModel):
    num1: float
    num2: float

# Root endpoint for basic testing
@app.get("/")
def read_root():
    return {"message": "Adder Application is running!"}

# Endpoint to perform addition
@app.post("/add")
def add_numbers(numbers: Numbers):
    result = numbers.num1 + numbers.num2
    return {"num1": numbers.num1, "num2": numbers.num2, "result": result}

# Run the app (if needed for standalone testing)
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)
