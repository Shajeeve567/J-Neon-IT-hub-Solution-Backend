from fastapi import FastAPI, UploadFile, HTTPException, File
import os
import uuid
import shutil
from db_utils import insert_document_record
import logging


logging.basicConfig(filename='app.log', level=logging.INFO)

app = FastAPI()

@app.post("/upload-doc")
def upload_document(file: UploadFile = File(...)):
    allowed_extensions = ['.pdf']
    file_extension = os.path.splitext(file.filename)[1].lower()

    if file_extension not in allowed_extensions:
        raise HTTPException(status_code=400, detail=f"Unsupported file type. Allowed types are: {', '.join(allowed_extensions)}")
    
    unique_filename =  f"{uuid.uuid4()}_{file.filename}"
    temp_file_path = os.path.join(os.getcwd(), unique_filename)

    try:
        # Save the uploaded file to a temporary file
        with open(temp_file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        file_id = insert_document_record(file.filename)
        print(f"Successfully saved file {file_id}")
        
    finally:
        if os.path.exists(temp_file_path):
            os.remove(temp_file_path)