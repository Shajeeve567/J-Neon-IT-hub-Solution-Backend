from fastapi import FastAPI, UploadFile, HTTPException, File
import os
import uuid
import shutil


app = FastAPI()


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
        
    finally:
        if os.path.exists(temp_file_path):
            os.remove(temp_file_path)