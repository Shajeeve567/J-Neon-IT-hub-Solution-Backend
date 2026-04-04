from fastapi import FastAPI, UploadFile, HTTPException, Fi
import os
import uuid
import shutil


app = FastAPI()


def upload_document(file: UploadFile = File(...)):
    unique_filename =  f"{uuid.uuid4()}_{file.filename}"
    temp_file_path = os.path.join(os.getcwd(), unique_filename)

    try:
        # Save the uploaded file to a temporary file
        with open(temp_file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)
        
    finally:
        if os.path.exists(temp_file_path):
            os.remove(temp_file_path)